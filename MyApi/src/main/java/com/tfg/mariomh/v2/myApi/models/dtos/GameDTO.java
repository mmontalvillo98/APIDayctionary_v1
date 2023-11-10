package com.tfg.mariomh.v2.myApi.models.dtos;

import com.tfg.mariomh.v2.myApi.types.PartOfSpeech;
import lombok.Data;

@Data
public class GameDTO {

    private String word;
    private String date;
    private String simPhWord0;
    private String simPhAudio0;
    private Short simPhSyllables0;
    private String simPhWord1;
    private String simPhAudio1;
    private Short simPhSyllables1;
    private String simPhWord2;
    private String simPhAudio2;
    private Short simPhSyllables2;
    private String simPhWord3;
    private String simPhAudio3;
    private Short simPhSyllables3;
    private String simDfWord0;
    private PartOfSpeech simDfType0;
    private String simDfText0;
    private String simDfWord1;
    private PartOfSpeech simDfType1;
    private String simDfText1;
    private String simDfWord2;
    private PartOfSpeech simDfType2;
    private String simDfText2;
    private String simDfWord3;
    private PartOfSpeech simDfType3;
    private String simDfText3;
    private String simSyWord0;
    private String simSyWord1;
    private String simSyWord2;
    private String simSyWord3;

}
