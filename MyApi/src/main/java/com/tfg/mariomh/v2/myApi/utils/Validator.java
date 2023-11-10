package com.tfg.mariomh.v2.myApi.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Validator {

    public final static String SPACE = " ";
    public final static Integer CERO = 0;
    public final static Integer SIMILAR_LIST_SIZE = 3;
    public final static Integer MINIGAME_SIZE = 4;

    public static Boolean objectsValid(Object ...objects){
        return objectsNotNull(objects) && Arrays.stream(objects).allMatch(Validator::objectValid);
    }

    public static Boolean objectValid(Object object){
        if(object instanceof String text){
            return text.trim().length() > CERO;
        }else if(object instanceof Integer num){
            return num > CERO;
        }else if(object instanceof Short num){
            return num > CERO;
        }else if(object instanceof List list){
            return list.size() == MINIGAME_SIZE;
        }
        return Boolean.TRUE;
    }

    public static Boolean objectsNotNull(Object ...objects){
        return Arrays.stream(objects).allMatch(Objects::nonNull);
    }

}
