package com.tfg.mariomh.v2.myApi.utils;

import java.util.Random;

public abstract class CodeGeneratorUtil {

    public static final Integer LENGTH = 10;
    public static final String CHARS = "abcdefghijklmnopqrstuwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public static String generateCode(){

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < LENGTH) {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            salt.append(CHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

}
