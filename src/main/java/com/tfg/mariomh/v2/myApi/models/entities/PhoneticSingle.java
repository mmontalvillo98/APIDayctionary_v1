package com.tfg.mariomh.v2.myApi.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneticSingle implements Word{
    private String word;
    private Phonetic phonetic;

    public PhoneticSingle(String word, String audio, Short syllables){
        this.word = word;
        this.phonetic = new Phonetic(audio, syllables);
    }

}
