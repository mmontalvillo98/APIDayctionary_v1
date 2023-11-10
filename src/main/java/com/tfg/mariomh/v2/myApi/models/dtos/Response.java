package com.tfg.mariomh.v2.myApi.models.dtos;

import com.google.gson.Gson;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public abstract class Response<T> {
    private List<T> entries;
    public Response(List<T> entries) {
        this.entries = entries;
    }
    protected static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        return new ArrayList<>(Arrays.asList(new Gson().fromJson(s, clazz)));
    }
    public static Boolean hasContent(Response response){
        return response!=null && response.getEntries()!=null && response.getEntries().size()>0;
    }
}
