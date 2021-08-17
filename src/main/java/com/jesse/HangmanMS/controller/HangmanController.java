package com.jesse.HangmanMS.controller;

import com.jesse.HangmanMS.model.HangmanGame;
import com.jesse.HangmanMS.repository.HangmanGameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HangmanController {

    @Autowired
    HangmanGameRepo repo;

    @GetMapping("/")
    public String index(Model model){
        HangmanGame game = new HangmanGame();
        game.setFinished(false);
        game.setWord("pie");
        game.setGuess(" _ _ _");
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
