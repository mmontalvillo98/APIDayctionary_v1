package com.tfg.mariomh.v2.myApi.services.apis.implementations;

import com.tfg.mariomh.v2.myApi.services.apis.interfaces.IRequester;
import com.tfg.mariomh.v2.myApi.utils.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public abstract class Requester <T> implements IRequester<T> {

    protected static final Short MANY_ELEMENTS = 1000;
    protected static final Integer FIRST = 0;

    protected final DictionaryApiServiceImpl dictionaryApiService;
    protected List<T> list;

    /**
     * Rellena la lista de elementos
     */
    public abstract void refillList();

    /**
     * Filtra la lista de elementos
     */
    public abstract void filterList();

    /**
     * Valida si la lista de elementos se encuentra vacía.
     * @return Boolean
     */
    protected Boolean emptyList(){
        return list == null || list.size() == Validator.CERO;
    }

    /**
     * Extrae un elemento de la lista y lo elimina de ella.
     * @return elemento
     */
    protected T popOfList(){
        T item = list.get(FIRST);
        list.remove(item);
        return item;
    }

}
