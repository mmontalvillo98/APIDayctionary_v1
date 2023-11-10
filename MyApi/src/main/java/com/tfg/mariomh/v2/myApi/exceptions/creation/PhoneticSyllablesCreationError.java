package com.tfg.mariomh.v2.myApi.exceptions.creation;

public class PhoneticSyllablesCreationError extends PhoneticCreationError{
    public PhoneticSyllablesCreationError(String word) {
        super(word, "Not found similar syllables");
    }
}
