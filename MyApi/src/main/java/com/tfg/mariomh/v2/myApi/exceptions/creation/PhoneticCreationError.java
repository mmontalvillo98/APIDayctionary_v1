package com.tfg.mariomh.v2.myApi.exceptions.creation;

import com.tfg.mariomh.v2.myApi.services.creators.implementations.PhoneticSimpleCreator;

public class PhoneticCreationError extends ObjectCreationError {

    public PhoneticCreationError(String word, String cause) {
        super(word, PhoneticSimpleCreator.class, cause);
    }
}
