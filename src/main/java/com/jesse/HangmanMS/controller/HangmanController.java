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

    @PostMapping("/nextLetter")
    public String nextLetter(Model model, @RequestParam(name = "hangmanGameId") long gameId,
                             @RequestParam(name = "nextLetter") String nextLetter){
    HangmanGame game = repo.findById(gameId).orElse(null);
    // calculate new game state

        // store updated game state
        game = repo.save(game);
    model.addAttribute("hangmanGame", game);
        return "index";
    }


}
