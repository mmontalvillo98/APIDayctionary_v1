package com.tfg.mariomh.v2.myApi.models.dtos;

import com.tfg.mariomh.v2.myApi.types.PartOfSpeech;
import lombok.Data;

import java.util.List;

@Data
public class DictionaryApiMeaningDTO {

    private PartOfSpeech partOfSpeech;
    private List<DictionaryApiDefinitionDTO> definitions;
    private List<String> synonyms;
    private List<String> antonyms;

}
