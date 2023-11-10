package com.tfg.mariomh.v2.myApi.services.creators.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.creation.ObjectCreationError;
import com.tfg.mariomh.v2.myApi.exceptions.requests.NoAvailableElementsError;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiResponseDTO;
import com.tfg.mariomh.v2.myApi.models.entities.DefinitionSingle;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.types.RelatedType;
import com.tfg.mariomh.v2.myApi.services.apis.implementations.RandomRequesterImpl;
import com.tfg.mariomh.v2.myApi.services.apis.implementations.RelatedRequesterImpl;
import com.tfg.mariomh.v2.myApi.services.creators.interfaces.ISingleCreator;
import com.tfg.mariomh.v2.myApi.utils.Validator;
import com.tfg.mariomh.v2.myApi.utils.WordValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DefinitionSingleCreator implements ISingleCreator<DefinitionSingle> {

    private final RelatedRequesterImpl relatedRequester;
    private final RandomRequesterImpl randomRequester;
    private final DefinitionSimpleCreator definitionSimpleCreator;

    @Override
    public List<DefinitionSingle> findSimilars(DictionaryApiResponseDTO dictionaryApiResponseDTO, Game game){
        List<DefinitionSingle> similarDefinitions = new ArrayList<>();
        fillListWithRelateds(dictionaryApiResponseDTO, game, similarDefinitions,
                new RelatedType[]{RelatedType.MEANING_LIKE, RelatedType.ASSOCIATED_WITH});
        fillListWithRandom(dictionaryApiResponseDTO, game, similarDefinitions);
        return similarDefinitions;
    }

    @Override
    public void fillListWithRelateds(DictionaryApiResponseDTO dictionaryApiResponseDTO, Game game, List<DefinitionSingle> similars, RelatedType[] relatedTypes){
        for (RelatedType relatedType: relatedTypes) {
            relatedRequester.setGameAndRelatedType(game, relatedType);
            fillListWithRelated(dictionaryApiResponseDTO, game, similars);
        }
    }

    @Override
    public void fillListWithRelated(DictionaryApiResponseDTO dictionaryApiResponseDTO, Game game, List<DefinitionSingle> similars){
        Boolean emptySearch = false;
        List<String> synonyms = getSynonyms(dictionaryApiResponseDTO);
        while(similars.size()<3 && emptySearch==false) {
            try {
                DefinitionSingle definitionSingle = findRelated(game);
                if(!WordValidator.present(definitionSingle, similars) && !WordValidator.listIncludes(synonyms, definitionSingle.getWord())){
                    similars.add(definitionSingle);
                }
            } catch (NoAvailableElementsError e) {
                emptySearch = true;
            }
        }
    }
    @Override
    public void fillListWithRandom(DictionaryApiResponseDTO dictionaryApiResponseDTO,Game game, List<DefinitionSingle> similarDefinitions){
        List<String> synonyms = getSynonyms(dictionaryApiResponseDTO);
        while(similarDefinitions.size()< Validator.SIMILAR_LIST_SIZE) {
            DefinitionSingle definitionSingle = findRandom(game);
            if(!WordValidator.present(definitionSingle, similarDefinitions) && !WordValidator.listIncludes(synonyms, definitionSingle.getWord())){
                similarDefinitions.add(definitionSingle);
            }
        }
    }
    @Override
    public DefinitionSingle findRelated(Game game) throws NoAvailableElementsError {
        try {
            return getSingle(game, relatedRequester.getDictionaryRegister());
        } catch (ObjectCreationError e) {
            return findRelated(game);
        }
    }
    @Override
    public DefinitionSingle findRandom(Game game){
        try {
            return getSingle(game, randomRequester.getDictionaryRegister());
        } catch (ObjectCreationError e) {
            return findRandom(game);
        }
    }
    @Override
    public DefinitionSingle getSingle(Game game, DictionaryApiResponseDTO dictionaryApiResponseDTO) throws ObjectCreationError {
        return new DefinitionSingle(dictionaryApiResponseDTO.getEntries().get(0).getWord(), definitionSimpleCreator.findAccordingTo(game, dictionaryApiResponseDTO));
    }
    public List<String> getSynonyms(DictionaryApiResponseDTO response){
        List<String> synonyms = new ArrayList<>();
        response.getEntries().stream().forEach(entry->{
            entry.getMeanings().stream().forEach(meanings->{
                synonyms.addAll(meanings.getSynonyms());
            });
        });
        return synonyms;
    }


}
