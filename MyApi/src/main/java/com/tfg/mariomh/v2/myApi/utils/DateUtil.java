package com.tfg.mariomh.v2.myApi.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static String CODE_PATTERN = "yyyyMMdd";
    private static String SHOW_PATTERN = "dd/MM/yyyy";
    private static DateTimeFormatter CODE_FORMATTER = DateTimeFormatter.ofPattern(CODE_PATTERN);
    private static DateTimeFormatter SHOW_FORMATTER = DateTimeFormatter.ofPattern(SHOW_PATTERN);

    public static String getTodayDate(){
        return formatDate(LocalDate.now());
    }
    public static String formatDate(LocalDate localDate){
        return localDate.format(CODE_FORMATTER);
    }
    public static String showDate(String date){
        return getDate(date).format(SHOW_FORMATTER);
    }
    public static LocalDate getDate(String date){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(CODE_PATTERN));
    }

}

