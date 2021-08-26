package com.jesse.HangmanMS.controller;

import com.jesse.HangmanMS.model.HangmanGame;
import com.jesse.HangmanMS.repository.HangmanGameRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class HangmanControllerTest {
    private HangmanController controller;
    private HangmanGameRepo mockRepo;


    @BeforeEach
    public void setup() {
        controller = new HangmanController();
        mockRepo = Mockito.mock(HangmanGameRepo.class);
        controller.setRepo(mockRepo);
    }

    @Test
    public void testNextLetter() {
        // test setup
        long testGameId = 42;
        HangmanGame game = new HangmanGame();
        game.setWord("catdog");
        game.setGuess(" _ _ _ _ _ _");
        game.setTries(0);
        game.setId(testGameId);

        // prepare a mock model, and repository
        Model mockModel = Mockito.mock(Model.class);

        // tell my mock repo what to do when the code calls it
        when(mockRepo.findById(testGameId)).thenReturn(Optional.of(game));
        when(mockRepo.save(Mockito.any(HangmanGame.class))).thenReturn(game);

        // run the actual code
        controller.nextLetter(mockModel, testGameId, "t");
        controller.nextLetter(mockModel, testGameId, "c");
        controller.nextLetter(mockModel, testGameId, "y");

        // asserts
        String expectedGuess = " c _ t _ _ _";
        int expectedTries = 1;
        assertEquals(expectedGuess, game.getGuess());
        assertEquals(expectedTries, game.getTries());
    }

}