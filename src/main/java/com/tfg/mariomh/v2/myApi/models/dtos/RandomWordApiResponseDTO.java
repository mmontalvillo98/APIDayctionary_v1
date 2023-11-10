package com.tfg.mariomh.v2.myApi.models.dtos;

public class RandomWordApiResponseDTO extends Response<String> {

    public RandomWordApiResponseDTO(String data){
        super(stringToArray(data, String[].class));
    }

}
