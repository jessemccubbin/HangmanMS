package com.jesse.HangmanMS.repository;


import com.jesse.HangmanMS.model.HangmanGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface HangmanGameRepo extends JpaRepository<HangmanGame, Long> {

    List<HangmanGame> findByFinished(boolean finished);
}
