package com.tfg.mariomh.v2.myApi.exceptions.creation;

import com.tfg.mariomh.v2.myApi.services.creators.implementations.SynonymCreator;

public class SynonymCreationError extends ObjectCreationError{
    public SynonymCreationError(String word, String cause) {
        super(word, SynonymCreator.class, cause);
    }
}
