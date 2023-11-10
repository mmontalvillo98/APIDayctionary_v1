package com.tfg.mariomh.v2.myApi.services.creators.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.creation.ObjectCreationError;
import com.tfg.mariomh.v2.myApi.exceptions.creation.SynonymNotFoundCreationError;
import com.tfg.mariomh.v2.myApi.exceptions.requests.NoAvailableElementsError;
import com.tfg.mariomh.v2.myApi.exceptions.requests.RequestApiError;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiResponseDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.types.RelatedType;
import com.tfg.mariomh.v2.myApi.services.apis.implementations.DictionaryApiServiceImpl;
import com.tfg.mariomh.v2.myApi.services.apis.implementations.RandomRequesterImpl;
import com.tfg.mariomh.v2.myApi.services.apis.implementations.RelatedRequesterImpl;
import com.tfg.mariomh.v2.myApi.utils.Validator;
import com.tfg.mariomh.v2.myApi.utils.WordValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SynonymCreator {

    private static final String SYNONYMS = "synonyms";
    private static final String ANTONYMS = "antonyms";
    private static final String ALL = "all";
    private final RandomRequesterImpl randomRequester;
    private final RelatedRequesterImpl relatedRequester;
    private final DictionaryApiServiceImpl dictionaryApiService;

    public String findSynonym(DictionaryApiResponseDTO response) throws ObjectCreationError {
        return findSynonym(response.getEntries().get(0).getWord(), getRelatedWords(response));
    }

    public String findSynonym(String word, Map<String, List<String>> relatedWords) throws SynonymNotFoundCreationError {
        if(relatedWords.get(SYNONYMS).size()>0){
            for(String synonym: relatedWords.get(SYNONYMS)){
                try {
                    dictionaryApiService.get(synonym);
                    return synonym;
                } catch (RequestApiError e) {
                    // Doesn't exist or ApiError
                }
            }
        }
        throw new SynonymNotFoundCreationError(word);
    }

    public List<String> findSimilars(Game game, DictionaryApiResponseDTO response){
        return findSimilars(game, getRelatedWords(response));
    }

    public List<String> findSimilars(Game game, Map<String, List<String>> relatedWords){
        List<String> similars = new ArrayList<>();
        fillListWithAntonyms(similars, relatedWords.get(ANTONYMS));
        fillListWithRelateds(game, similars, relatedWords, new RelatedType[]{RelatedType.ASSOCIATED_WITH});
        fillListWithRandom(similars, relatedWords.get(ALL));
        return similars;
    }

    public void fillListWithAntonyms(List<String> similars, List<String> antonyms){
        Integer maxAddition = new Random().nextInt(2);
        for(String antonym: antonyms){
            try {
                dictionaryApiService.get(antonym);
                if(similars.size() < maxAddition && !WordValidator.listIncludes(similars, antonym)) {
                    similars.add(antonym);
                }
            } catch (RequestApiError e) {
                // Doesn't exist or ApiError
            }
        }
    }

    public void fillListWithRelateds(Game game, List<String> similars, Map<String, List<String>> relatedWords, RelatedType[] relatedTypes){
        for (RelatedType relatedType: relatedTypes) {
            relatedRequester.setGameAndRelatedType(game, relatedType);
            fillListWithRelated(similars, relatedWords);
        }
    }

    private void fillListWithRelated(List<String> similars, Map<String, List<String>> relatedWords){
        Boolean emptySearch = false;
        while(similars.size()<Validator.SIMILAR_LIST_SIZE && !emptySearch){
            try {
                String related = relatedRequester.get().getWord();
                if(!relatedWords.get(ALL).contains(related)
                        && !WordValidator.listIncludes(similars, related)){
                    dictionaryApiService.get(related);
                    similars.add(related);
                }
            } catch (NoAvailableElementsError e) {
                emptySearch = true;
            } catch (RequestApiError e) {
                // Doesn't exist
            }
        }
    }

    public void fillListWithRandom(List<String> similars, List<String> relatedWords){
        while(similars.size()<Validator.SIMILAR_LIST_SIZE) {
            DictionaryApiResponseDTO dictionaryResponse = randomRequester.getDictionaryRegister();
            String word = dictionaryResponse.getEntries().get(0).getWord();
            if(!similars.contains(word)
                    && !relatedWords.contains(word)
                    && !WordValidator.listIncludes(similars,word)){
                similars.add(word);
            }
        }
    }

    public Map<String, List<String>> getRelatedWords(DictionaryApiResponseDTO response){
        Map<String, List<String>> relatedWords = new HashMap<>();
        relatedWords.put(SYNONYMS, new ArrayList<>());
        relatedWords.put(ANTONYMS, new ArrayList<>());
        relatedWords.put(ALL, new ArrayList<>());
        response.getEntries().stream().forEach(entry->{
            entry.getMeanings().stream().forEach(meanings->{
                relatedWords.get(SYNONYMS).addAll(meanings.getSynonyms());
                relatedWords.get(ANTONYMS).addAll(meanings.getAntonyms());
            });
        });
        relatedWords.put(SYNONYMS, filterList(response.getEntries().get(0).getWord(), relatedWords.get(SYNONYMS)));
        relatedWords.put(ANTONYMS, filterList(response.getEntries().get(0).getWord(), relatedWords.get(ANTONYMS)));
        relatedWords.get(ALL).addAll(relatedWords.get(SYNONYMS));
        relatedWords.get(ALL).addAll(relatedWords.get(ANTONYMS));
        return relatedWords;
    }

    public List<String> filterList(String word, List<String> list){
        return list.stream()
                .filter(Validator::objectValid)
                .filter(item->!item.contains(" "))
                .filter(item->!item.contains("-"))
                .filter(item->WordValidator.different(word, item))
                .collect(Collectors.toList());
    }

}
