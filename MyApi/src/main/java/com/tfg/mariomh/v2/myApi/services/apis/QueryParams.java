package com.tfg.mariomh.v2.myApi.services.apis;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueryParams {
    private List<QueryParam> parameters;
    public QueryParams(QueryParam ...parameters) {
        this.parameters = new ArrayList<>();
        for (QueryParam param: parameters) {
            add(param);
        }
    }
    /**
     * Crea los query params de una petición
     * @return String cadena de texto a añadir a la url
     */
    public String getQueryParams(){
        StringBuilder queryParams = new StringBuilder("?");
        parameters.forEach(param->{
            queryParams.append(param.getQueryParam()+"&");
        });
        queryParams.delete(queryParams.length()-1, queryParams.length());
        return queryParams.toString();
    }
    public void add(QueryParam param){
        this.parameters.add(param);
    }
}
