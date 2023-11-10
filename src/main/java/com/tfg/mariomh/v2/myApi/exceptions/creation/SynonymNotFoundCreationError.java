package com.tfg.mariomh.v2.myApi.exceptions.creation;

public class SynonymNotFoundCreationError extends SynonymCreationError{
    public SynonymNotFoundCreationError(String word) {
        super(word, "Synonym not found");
    }
}
