package com.tfg.mariomh.v2.myApi.services.creators.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.creation.ObjectCreationError;
import com.tfg.mariomh.v2.myApi.exceptions.requests.NoAvailableElementsError;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiResponseDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.models.entities.PhoneticSingle;
import com.tfg.mariomh.v2.myApi.types.RelatedType;
import com.tfg.mariomh.v2.myApi.services.apis.implementations.RandomRequesterImpl;
import com.tfg.mariomh.v2.myApi.services.apis.implementations.RelatedRequesterImpl;
import com.tfg.mariomh.v2.myApi.services.creators.interfaces.ISingleCreator;
import com.tfg.mariomh.v2.myApi.utils.Validator;
import com.tfg.mariomh.v2.myApi.utils.WordValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PhoneticSingleCreator implements ISingleCreator<PhoneticSingle> {

    private final RelatedRequesterImpl relatedRequester;
    private final RandomRequesterImpl randomRequester;
    private final PhoneticSimpleCreator phoneticSimpleCreator;

    @Override
    public List<PhoneticSingle> findSimilars(DictionaryApiResponseDTO dictionaryApiResponseDTO, Game game){
        List<PhoneticSingle> similarPhonetics = new ArrayList<>();
        fillListWithRelateds(dictionaryApiResponseDTO, game, similarPhonetics,
                new RelatedType[]{RelatedType.SOUND_LIKE, RelatedType.RIMES_WITH});
        fillListWithRandom(dictionaryApiResponseDTO, game, similarPhonetics);
        return similarPhonetics;
    }
    @Override
    public void fillListWithRelateds(DictionaryApiResponseDTO dictionaryApiResponseDTO,Game game, List<PhoneticSingle> similars, RelatedType[] relatedTypes){
        for (RelatedType relatedType: relatedTypes) {
            relatedRequester.setGameAndRelatedType(game, relatedType);
            fillListWithRelated(dictionaryApiResponseDTO, game, similars);
        }
    }
    @Override
    public void fillListWithRelated(DictionaryApiResponseDTO dictionaryApiResponseDTO,Game game, List<PhoneticSingle> similars){
        Boolean emptySearch = false;
        while(similars.size()<3 && emptySearch==false) {
            try {
                PhoneticSingle phoneticSingle = findRelated(game);
                if(!WordValidator.present(phoneticSingle, similars)){
                    similars.add(phoneticSingle);
                }
            } catch (NoAvailableElementsError e) {
                emptySearch = true;
            }
        }
    }
    @Override
    public void fillListWithRandom(DictionaryApiResponseDTO dictionaryApiResponseDTO,Game game, List<PhoneticSingle> similars){
        while(similars.size()< Validator.SIMILAR_LIST_SIZE) {
            PhoneticSingle phoneticSingle = findRandom(game);
            if(!WordValidator.present(phoneticSingle, similars)){
                similars.add(phoneticSingle);
            }
        }
    }
    @Override
    public PhoneticSingle findRelated(Game game) throws NoAvailableElementsError {
        try {
            return getSingle(game, relatedRequester.getDictionaryRegister());
        } catch (ObjectCreationError e) {
            return findRelated(game);
        }
    }
    @Override
    public PhoneticSingle findRandom(Game game){
        try {
            return getSingle(game, randomRequester.getDictionaryRegister());
        } catch (ObjectCreationError e) {
            return findRandom(game);
        }
    }
    @Override
    public PhoneticSingle getSingle(Game game, DictionaryApiResponseDTO dictionaryApiResponseDTO) throws ObjectCreationError {
        return new PhoneticSingle(dictionaryApiResponseDTO.getEntries().get(0).getWord(), phoneticSimpleCreator.findAccordingTo(game, dictionaryApiResponseDTO));
    }

}
