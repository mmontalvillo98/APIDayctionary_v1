package com.tfg.mariomh.v2.myApi.services.apis.interfaces;

import com.tfg.mariomh.v2.myApi.exceptions.requests.NoAvailableElementsError;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiResponseDTO;

public interface IRequester<T>{
    /**
     * Obtiene un elemento
     * @return elemento
     * @throws NoAvailableElementsError
     */
    T get() throws NoAvailableElementsError;

    /**
     * Obtiene una entrada de diccionario del primer elemento del que exista
     * @return DictionaryApiResponseDTO entrada de diccionario
     * @throws NoAvailableElementsError
     */
    DictionaryApiResponseDTO getDictionaryRegister() throws NoAvailableElementsError;

 }
