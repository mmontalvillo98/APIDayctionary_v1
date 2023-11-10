package com.tfg.mariomh.v2.myApi.models.dtos;

import com.tfg.mariomh.v2.myApi.models.entities.Phonetic;
import com.tfg.mariomh.v2.myApi.models.entities.Word;
import lombok.Data;

import java.util.List;

@Data
public class DictionaryApiEntryDTO implements Word {

    private String word;
    private String phonetic;
    private List<Phonetic> phonetics;
    private List<DictionaryApiMeaningDTO> meanings;

}
