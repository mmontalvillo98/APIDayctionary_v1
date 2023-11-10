package com.tfg.mariomh.v2.myApi.utils;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CodeGeneratorUtilTest {

    @Test
    public void generateCode(){
        String generatedCode = CodeGeneratorUtil.generateCode();
        Assertions.assertEquals(10, generatedCode.length());
        for(char character: generatedCode.toCharArray()){
            Assertions.assertTrue(CodeGeneratorUtil.CHARS.contains(String.valueOf(character)));
        }
    }

}
