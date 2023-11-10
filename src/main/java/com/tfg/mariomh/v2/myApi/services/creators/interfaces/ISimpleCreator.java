package com.tfg.mariomh.v2.myApi.services.creators.interfaces;

import com.tfg.mariomh.v2.myApi.exceptions.creation.ObjectCreationError;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiEntryDTO;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiResponseDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Game;

public interface ISimpleCreator<T> {

    default T find(DictionaryApiResponseDTO response) throws ObjectCreationError {
        ObjectCreationError error = null;
        for (DictionaryApiEntryDTO entry : response.getEntries()) {
            try {
                return find(entry);
            } catch (ObjectCreationError e) {
                error = e;
            }
        }
        throw error;
    }

    T find(DictionaryApiEntryDTO entry) throws ObjectCreationError;

    default T findAccordingTo(Game game, DictionaryApiResponseDTO response) throws ObjectCreationError {
        ObjectCreationError error = null;
        for (DictionaryApiEntryDTO entry : response.getEntries()) {
            try {
                return findAccordingTo(game, entry);
            } catch (ObjectCreationError e) {
                error = e;
            }
        }
        throw error;
    }

    T findAccordingTo(Game game, DictionaryApiEntryDTO entry) throws ObjectCreationError;

}