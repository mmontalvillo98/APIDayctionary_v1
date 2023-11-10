package com.tfg.mariomh.v2.myApi.models.dtos;

import com.tfg.mariomh.v2.myApi.models.entities.Word;
import lombok.Data;

@Data
public class DatamuseApiEntryDTO implements Word {

    private String word;
    private Long score;
    private Short numSyllables;

}
