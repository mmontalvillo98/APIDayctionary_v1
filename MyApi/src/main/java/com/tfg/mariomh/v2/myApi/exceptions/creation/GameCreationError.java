package com.tfg.mariomh.v2.myApi.exceptions.creation;

import com.tfg.mariomh.v2.myApi.services.creators.GameCreator;

public class GameCreationError extends ObjectCreationError{

    public GameCreationError(String word, String cause) {
        super(word, GameCreator.class, cause);
    }
}
