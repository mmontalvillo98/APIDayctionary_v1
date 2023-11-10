package com.tfg.mariomh.v2.myApi.types;

import com.google.gson.annotations.SerializedName;

public enum PartOfSpeech {

    @SerializedName("noun")NOUN,
    @SerializedName("pronoun")PRONOUN,
    @SerializedName("verb")VERB,
    @SerializedName("adjective")ADJECTIVE,
    @SerializedName("adverb")ADVERB,
    @SerializedName("preposition")PREPOSITION,
    @SerializedName("conjunction")CONJUNCTION,
    @SerializedName("interjection")INTERJECTION

}
