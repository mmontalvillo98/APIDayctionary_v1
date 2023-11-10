package com.tfg.mariomh.v2.myApi.utils;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class WordValidatorTest {

    private final static String LESS_THAN_SMALL_WORD = "by";
    private final static String BASE_LIMIT_WORD = "are";
    private final static String SMALL_BASE_LIMIT_WORD = "love";
    private final static String SMALL_TOP_LIMIT_WORD = "loves";
    private final static String MID_BASE_LIMIT_WORD = "wonder";
    private final static String MID_TOP_LIMIT_WORD = "teachers";
    private final static String LARGE_BASE_LIMIT_WORD = "absolute";
    private final static String VERY_LARGE_WORD = "enviroment";


    @Test
    public void getWordWithoutLettersTest(){
        List<String> words = WordValidator.getWordWithoutLetters(SMALL_TOP_LIMIT_WORD, 1);
        Assertions.assertEquals(2, words.size());
        Assertions.assertEquals("love", words.get(0));
        Assertions.assertEquals("oves", words.get(1));
        words = WordValidator.getWordWithoutLetters(MID_BASE_LIMIT_WORD, 2);
        Assertions.assertEquals(2, words.size());
        Assertions.assertEquals("wond", words.get(0));
        Assertions.assertEquals("nder", words.get(1));
        words = WordValidator.getWordWithoutLetters(MID_TOP_LIMIT_WORD, 3);
        Assertions.assertEquals(2, words.size());
        Assertions.assertEquals("teach", words.get(0));
        Assertions.assertEquals("chers", words.get(1));
        words = WordValidator.getWordWithoutLetters(VERY_LARGE_WORD, 4);
        Assertions.assertEquals(2, words.size());
        Assertions.assertEquals("enviro", words.get(0));
        Assertions.assertEquals("roment", words.get(1));
    }

    @Test
    public void getRootsTest(){
        Assertions.assertEquals(0, WordValidator.getRoots(LESS_THAN_SMALL_WORD).size());
        Assertions.assertEquals(1, WordValidator.getRoots(BASE_LIMIT_WORD).size());
        Assertions.assertEquals(3, WordValidator.getRoots(SMALL_BASE_LIMIT_WORD).size());
        Assertions.assertEquals(3, WordValidator.getRoots(SMALL_TOP_LIMIT_WORD).size());

    }

}
