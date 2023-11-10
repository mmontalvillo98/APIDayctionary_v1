package com.tfg.mariomh.v2.myApi.exceptions.creation;

public abstract class ObjectCreationError extends Exception {

    public ObjectCreationError(String word, Class creator, String cause){
        super(String.format("%s: '%s' not found (%s)", word, creator.toString(), cause));
    }

}
