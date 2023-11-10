package com.tfg.mariomh.v2.myApi.services.apis.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.requests.RequestApiError;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiResponseDTO;
import com.tfg.mariomh.v2.myApi.utils.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RandomRequesterImpl extends Requester<String> {

    private static final Integer MIN = 0;
    private static final Integer MAX = 1;
    private final RandomWordApiServiceImpl randomWordApiService;

    public RandomRequesterImpl(DictionaryApiServiceImpl dictionaryApiService, List<String> list, RandomWordApiServiceImpl randomWordApiService) {
        super(dictionaryApiService, list);
        this.randomWordApiService = randomWordApiService;
    }

    @Override
    public String get(){
        if (emptyList()) {
            refillList();
            filterList();
            return get(); // In case filter removes all elements of list
        }
        return popOfList();
    }

    @Override
    public DictionaryApiResponseDTO getDictionaryRegister(){
        String word = get();
        try {
            return dictionaryApiService.get(word);
        } catch (RequestApiError e) {
            return getDictionaryRegister();
        }
    }

    @Override
    public void refillList() {
        try {
            list = randomWordApiService.getSome(MANY_ELEMENTS).getEntries();
        } catch (RequestApiError e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void filterList() {
        list = list.stream()
                .filter( word -> word.split(Validator.SPACE).length > Validator.CERO)
                .collect(Collectors.toList());
    }
}
