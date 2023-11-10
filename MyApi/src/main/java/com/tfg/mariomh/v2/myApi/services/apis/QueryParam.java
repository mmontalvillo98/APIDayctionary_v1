package com.tfg.mariomh.v2.myApi.services.apis;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueryParam {
    private String key;
    private String value;
    public String getQueryParam() {
        return key+"="+value;
    }
}
