package com.tfg.mariomh.v2.myApi.exceptions.creation;

import com.tfg.mariomh.v2.myApi.services.creators.implementations.DefinitionSimpleCreator;

public class DefinitionCreationError extends ObjectCreationError{
    public DefinitionCreationError(String word) {
        super(word, DefinitionSimpleCreator.class, ("Not found valid part of speech and/or definition"));
    }
}
