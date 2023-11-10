package com.tfg.mariomh.v2.myApi.exceptions.creation;

public class PhoneticAudioCreationError extends PhoneticCreationError{

    public PhoneticAudioCreationError(String word) {
        super(word, "Not found valid audio");
    }
}
