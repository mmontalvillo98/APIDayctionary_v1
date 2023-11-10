package com.tfg.mariomh.v2.myApi.models.dtos;

public class DatamuseApiResponseDTO extends Response<DatamuseApiEntryDTO> {

    public DatamuseApiResponseDTO(String data){
        super(stringToArray(data, DatamuseApiEntryDTO[].class));
    }

}
