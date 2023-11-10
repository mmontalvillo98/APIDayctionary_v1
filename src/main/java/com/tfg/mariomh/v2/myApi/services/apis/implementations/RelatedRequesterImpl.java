package com.tfg.mariomh.v2.myApi.services.apis.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.requests.NoAvailableElementsError;
import com.tfg.mariomh.v2.myApi.exceptions.requests.RequestApiError;
import com.tfg.mariomh.v2.myApi.models.dtos.DatamuseApiEntryDTO;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiResponseDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.types.RelatedType;
import com.tfg.mariomh.v2.myApi.utils.Validator;
import com.tfg.mariomh.v2.myApi.utils.WordValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatedRequesterImpl extends Requester<DatamuseApiEntryDTO>{

    private final DatamuseApiServiceImpl datamuseApiService;
    protected Game game;
    protected RelatedType relatedType;

    public RelatedRequesterImpl(DictionaryApiServiceImpl dictionaryApiService, List<DatamuseApiEntryDTO> list, DatamuseApiServiceImpl datamuseApiService) {
        super(dictionaryApiService, list);
        this.datamuseApiService = datamuseApiService;
    }

    public void setGameAndRelatedType(Game game, RelatedType relatedType){
        this.game = game;
        this.relatedType = relatedType;
        list = new ArrayList<>();
        refillList();
        filterList();
    }

    public DictionaryApiResponseDTO getDictionaryRegister() throws NoAvailableElementsError{
        DatamuseApiEntryDTO word = get();
        try {
            return dictionaryApiService.get(word.getWord());
        } catch (RequestApiError e) {
            return getDictionaryRegister();
        }
    }

    public DatamuseApiEntryDTO get() throws NoAvailableElementsError{
        if (emptyList()) {
            throw new NoAvailableElementsError(RelatedRequesterImpl.class, relatedType.toString());
        }
        return popOfList();
    }

    @Override
    public void refillList(){
        try {
            search();
        } catch (RequestApiError e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void filterList(){
        list = list.stream()
                .filter( word -> WordValidator.different(game, word))
                .filter( word -> word.getWord().split(Validator.SPACE).length > Validator.CERO)
                .collect(Collectors.toList());
    }

    private void search() throws RequestApiError {
        switch(relatedType){
            case ASSOCIATED_WITH -> list = datamuseApiService.getAsociatedWith(game).getEntries();
            case MEANING_LIKE -> list = datamuseApiService.getMeaningLike(game).getEntries();
            case RIMES_WITH -> list = datamuseApiService.getRimesWith(game).getEntries();
            case SOUND_LIKE -> list = datamuseApiService.getSoundLike(game).getEntries();
        }
    }

}
