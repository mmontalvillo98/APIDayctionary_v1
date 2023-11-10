package com.tfg.mariomh.v2.myApi.models.dtos;

import lombok.Data;

import java.util.List;

@Data
public class DictionaryApiDefinitionDTO {

    private String definition;
    private String example;
    private List<String> synonyms;
    private List<String> antonyms;

}
