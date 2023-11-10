package com.tfg.mariomh.v2.myApi.exceptions.requests;

public class NoAvailableElementsError extends RequestApiError{
    public NoAvailableElementsError(Class apiService, String reason) {
        super(404, apiService, reason);
    }
}
