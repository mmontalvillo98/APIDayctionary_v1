package com.tfg.mariomh.v2.myApi.utils;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RunWith(JUnit4.class)
public class DateUtilTest {

    @Test
    public void getTodayDateTest(){
        Assertions.assertEquals(DateUtil.formatDate(LocalDate.now()), DateUtil.getTodayDate());
    }
    @Test
    public void formatDateTest(){
        Assertions.assertEquals("20230904", DateUtil.formatDate(LocalDate.of(2023, 9, 4)));
        Assertions.assertThrows(DateTimeException.class, ()->DateUtil.formatDate(LocalDate.of(2023, 9, 32)));
        Assertions.assertThrows(DateTimeException.class, ()->DateUtil.formatDate(LocalDate.of(2023, 0, 1)));
        Assertions.assertThrows(DateTimeException.class, ()->DateUtil.formatDate(LocalDate.of(2023, 12, 0)));
    }
    @Test
    public void showDateTest(){
        Assertions.assertEquals("04/09/2023", DateUtil.showDate("20230904"));
        Assertions.assertThrows(DateTimeException.class, ()->DateUtil.getDate("20230932"));
        Assertions.assertThrows(DateTimeException.class, ()->DateUtil.getDate("20230001"));
        Assertions.assertThrows(DateTimeException.class, ()->DateUtil.getDate("20231200"));
    }
    @Test
    public void getDateTest(){
        Assertions.assertEquals(LocalDate.parse("20230904",
                DateTimeFormatter.ofPattern("yyyyMMdd")), DateUtil.getDate("20230904"));
        Assertions.assertThrows(DateTimeException.class, ()->DateUtil.getDate("20230932"));
        Assertions.assertThrows(DateTimeException.class, ()->DateUtil.getDate("20230001"));
        Assertions.assertThrows(DateTimeException.class, ()->DateUtil.getDate("20231200"));
    }

}
