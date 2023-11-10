package com.tfg.mariomh.v2.myApi.dependencies;

import opennlp.tools.stemmer.PorterStemmer;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PorterStemmerTest {

    String pageWordList[] = { "draft", "drafted", "drafting", "drafts", "drafty", "draftsman" };
    String pageStemWordList[] = { "draft", "draft", "draft", "draft", "drafti", "draftsman" };
    String[] myWordList = {"lovely", "worker", "buddy", "shorts", "moon", "probable", "environment"};
    String[] myStemWordList = {"love", "worker", "buddi", "short", "moon", "probabl", "environ"};

    @Test
    public void pageStemTest(){
        PorterStemmer stemmer = new PorterStemmer();
        for(int i = 0; i< pageWordList.length; i++){
            String stemWord = stemmer.stem(pageWordList[i]);
            assertEquals(pageStemWordList[i], stemWord);
        }
    }

    @Test
    public void myStemTest(){
        PorterStemmer stemmer = new PorterStemmer();
        for(int i = 0; i< myWordList.length; i++){
            String stemWord = stemmer.stem(myWordList[i]);
            assertEquals(myStemWordList[i], stemWord);
        }
    }

}
