package com.jesse.HangmanMS.controller;

import com.jesse.HangmanMS.model.HangmanGame;
import com.jesse.HangmanMS.repository.HangmanGameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Controller
public class HangmanController {

    @Autowired
    HangmanGameRepo repo;

    @GetMapping("/")
    public String index(Model model) throws Exception{
        HangmanGame game = new HangmanGame();

        Resource txtFile = new ClassPathResource("engmix.txt");
        InputStream inputStream = txtFile.getInputStream();

        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
               words.add(line);
            }
        }
        int wordIndex = ((int) (Math.random() * words.size()));
        String wordGuess = words.get(wordIndex);
        String hiddenWord = " _".repeat(wordGuess.length()).trim();

        game.setGuess(hiddenWord);
        game.setWord(wordGuess);
        game.setFinished(false);
        game.setTries(0);

        game = repo.save(game);

        model.addAttribute("hangmanGame", game);
        return "index";

    }

    @PostMapping("/nextGuess")
    public String nextLetter(Model model, @RequestParam(name = "hangmanGameId") long gameId,
                             @RequestParam(name = "nextLetter") String nextLetter) {

        HangmanGame game = repo.findById(gameId).orElse(null);
        // calculate new game state
        boolean found = false;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < game.getWord().length(); i++) {
            if (nextLetter.charAt(0) == game.getWord().charAt(i)) {
               sb.append(" ").append(game.getWord().charAt(i));
                found = true;
            } else {
                sb.append(" " + game.getGuess().charAt((i * 2) + 1));
            }
        }

        game.setGuess(sb.toString());

        if (!found) {
            int newTries = game.getTries() + 1;
            game.setTries(newTries);
        }

        if (game.getTries() > 6) {
            game.setFinished(true);
        }

        if (!game.getGuess().contains("_")) {
            game.setSolved(true);
            game.setFinished(true);
        }

            // store updated game state
            game = repo.save(game);
            model.addAttribute("hangmanGame", game);
            return "index";
        }
    // todo
    }