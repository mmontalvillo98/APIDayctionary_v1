package com.tfg.mariomh.v2.myApi.utils;

import com.tfg.mariomh.v2.myApi.models.entities.Game;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class ValidatorTest {

    @Test
    public void objectsNotNullTest(){
        Assertions.assertTrue(Validator.objectsNotNull());
        Game game = null;
        Assertions.assertFalse(Validator.objectsNotNull(game));
        game = new Game();
        Assertions.assertTrue(Validator.objectsNotNull(game));
        Assertions.assertFalse(Validator.objectsNotNull(game.getWord(), game.getDefinition()));
        Assertions.assertFalse(Validator.objectsNotNull(game, game.getPhonetic()));
    }

    @Test
    public void objectValidTest(){
        Assertions.assertFalse(Validator.objectValid(""));
        Assertions.assertFalse(Validator.objectValid("   "));
        Assertions.assertFalse(Validator.objectValid("  "));
        Assertions.assertFalse(Validator.objectValid("         "));
        Assertions.assertTrue(Validator.objectValid("text"));
        Assertions.assertFalse(Validator.objectValid((short)-1));
        Assertions.assertFalse(Validator.objectValid((short)0));
        Assertions.assertTrue(Validator.objectValid((short)1));
        Assertions.assertFalse(Validator.objectValid(-1));
        Assertions.assertFalse(Validator.objectValid(0));
        Assertions.assertTrue(Validator.objectValid(1));
        Assertions.assertFalse(Validator.objectValid(List.of()));
        Assertions.assertFalse(Validator.objectValid(List.of("a")));
        Assertions.assertFalse(Validator.objectValid(List.of("a", "b")));
        Assertions.assertFalse(Validator.objectValid(List.of("a", "b", "c")));
        Assertions.assertFalse(Validator.objectValid(List.of("a", "b", "c", "d", "e")));
        Assertions.assertTrue(Validator.objectValid(List.of("a", "b", "c", "d")));
    }

    @Test
    public void objectsValidTest(){
        Assertions.assertTrue(Validator.objectsValid());
        Game game = null;
        Assertions.assertFalse(Validator.objectsValid(game));
        game = new Game();
        Assertions.assertTrue(Validator.objectsValid(game));
        Assertions.assertFalse(Validator.objectsValid(game.getWord(), game.getDefinition()));
        Assertions.assertFalse(Validator.objectsValid(game, game.getPhonetic()));
        Assertions.assertFalse(Validator.objectsValid(""));
        Assertions.assertFalse(Validator.objectsValid("   "));
        Assertions.assertFalse(Validator.objectsValid("  "));
        Assertions.assertFalse(Validator.objectsValid("         "));
        Assertions.assertTrue(Validator.objectsValid("text"));
        Assertions.assertFalse(Validator.objectsValid((short)-1));
        Assertions.assertFalse(Validator.objectsValid((short)0));
        Assertions.assertTrue(Validator.objectsValid((short)1));
        Assertions.assertFalse(Validator.objectsValid(-1));
        Assertions.assertFalse(Validator.objectsValid(0));
        Assertions.assertTrue(Validator.objectsValid(1));
        Assertions.assertFalse(Validator.objectsValid(List.of()));
        Assertions.assertFalse(Validator.objectsValid(List.of("a")));
        Assertions.assertFalse(Validator.objectsValid(List.of("a", "b")));
        Assertions.assertFalse(Validator.objectsValid(List.of("a", "b", "c")));
        Assertions.assertFalse(Validator.objectsValid(List.of("a", "b", "c", "d", "e")));
        Assertions.assertTrue(Validator.objectsValid(List.of("a", "b", "c", "d")));
    }

}
