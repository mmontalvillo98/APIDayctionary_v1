package com.tfg.mariomh.v2.myApi.models.dtos;

public class DictionaryApiResponseDTO extends Response<DictionaryApiEntryDTO> {

    public DictionaryApiResponseDTO(String data){
        super(stringToArray(data, DictionaryApiEntryDTO[].class));
    }

}
