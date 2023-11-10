package com.tfg.mariomh.v2.myApi.utils;

import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.models.entities.Word;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordValidator {

    private final static Integer MIN_LETTERS = 3;
    private final static Integer SMALL_WORD = 4;
    private final static Integer MID_WORD = 6;
    private final static Integer LARGE_WORD = 9;

    public static Boolean different(Word word1, Word word2){
        return different(word1.getWord(), word2.getWord());
    }

    public static Boolean different(Word word1, String rawWord2){
        Game word2 = new Game();
        word2.setWord(rawWord2);
        return different(word1, word2);
    }

    public static Boolean different(String word1, String word2){
        return !word1.equals(word2) && rootsNotMatch(word1, word2);
    }

    private static Boolean rootsNotMatch(String word1, String word2){
        return !rootsMatch(word1, word2) && !rootsMatch(word2, word1);
    }

    private static Boolean rootsMatch(String word1, String word2){
        for(String root: getRoots(word1)){
            if(word2.startsWith(root) || word2.endsWith(root)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static List<String> getWordWithoutLetters(String word, Integer letters){
        final Integer beggining = letters;
        final Integer end = word.length() - letters;
        return List.of(
                word.substring(0, end),
                word.substring(beggining)
        );
    }

    public static void addRootsIfMinLetters(String word, List<String> roots){
        if(word.length() >= MIN_LETTERS){
            roots.add(word);
        }
    }
    public static void addRootsIfSmallWord(String word, List<String> roots){
        if(word.length() >= SMALL_WORD){
            roots.addAll(getWordWithoutLetters(word, 1));
        }
    }
    public static void addRootsIfMidWord(String word, List<String> roots){
        if(word.length() >= MID_WORD) {
            Integer sixtyPercent = (int) Math.ceil(word.length()*0.4);
            roots.addAll(getWordWithoutLetters(word, sixtyPercent));
            roots.addAll(getWordWithoutLetters(word, 2));
        }
    }
    public static void addRootsIfLargeWord(String word, List<String> roots){
        if(word.length() >= LARGE_WORD){
            roots.addAll(getWordWithoutLetters(word, word.length()/2));
            roots.addAll(getWordWithoutLetters(word, 3));
            roots.addAll(getWordWithoutLetters(word, 4));
        }
    }

    public static List<String> getRoots(String word){
        List<String> roots = new ArrayList<>();
        addRootsIfMinLetters(word, roots);
        addRootsIfSmallWord(word, roots);
        addRootsIfMidWord(word, roots);
        addRootsIfLargeWord(word, roots);
        return roots;
    }

    public static Boolean allWordsDifferent(List<? extends Word> list){
        return allDifferent(list.stream().map(Word::getWord).collect(Collectors.toList()));
    }

    public static Boolean allDifferent(List<String> list){
        List<String> words = new ArrayList<>();
        for (String elem: list) {
            if(present(elem, words)){
                return Boolean.FALSE;
            }
            words.add(elem);
        }
        return Boolean.TRUE;
    }

    public static Boolean present(Word word, List<? extends Word> list){
        return present(word.getWord(), list.stream().map(Word::getWord).collect(Collectors.toList()));
    }
    public static Boolean present(String word, List<String> list){
        return list.stream().filter(item->!different(word, item)).findAny().isPresent();
    }

    public static Boolean listWordIncludes(List<? extends Word> list, String word){
        return listIncludes(list.stream().toList().stream().map(Word::getWord).collect(Collectors.toList()), word);
    }

    public static Boolean listIncludes(List<String> list, String word){
        return list.stream()
                .map(item->{
                    Game game = new Game();
                    game.setWord(item);
                    return game;
                })
                .anyMatch(item->!different(item, word));
    }

    public static Boolean wordInPhrase(Word word, String phrase){
        for(String phraseWord: phrase.split(Validator.SPACE)){
            if(!different(word, phraseWord)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static Boolean wordInPhrase(String word, String phrase){
        for(String phraseWord: phrase.split(Validator.SPACE)){
            if(!different(word, phraseWord)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

}
