package com.tfg.mariomh.v2.myApi.services.creators.interfaces;

import com.tfg.mariomh.v2.myApi.exceptions.creation.ObjectCreationError;
import com.tfg.mariomh.v2.myApi.exceptions.requests.NoAvailableElementsError;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiResponseDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.types.RelatedType;

import java.util.List;

public interface ISingleCreator<T>{
    List<T> findSimilars(DictionaryApiResponseDTO dictionaryApiResponseDTO, Game game);
    void fillListWithRelateds(DictionaryApiResponseDTO dictionaryApiResponseDTO, Game game, List<T> similars, RelatedType[] relatedTypes);
    void fillListWithRelated(DictionaryApiResponseDTO dictionaryApiResponseDTO, Game game, List<T> similarDefinitions);
    void fillListWithRandom(DictionaryApiResponseDTO dictionaryApiResponseDTO, Game game, List<T> similarDefinitions);
    T findRelated(Game game) throws NoAvailableElementsError;
    T findRandom(Game game);
    T getSingle(Game game, DictionaryApiResponseDTO dictionaryApiResponseDTO) throws ObjectCreationError;
}
