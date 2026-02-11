package com.example.Practica2DAAW;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public List<Score> getTopScores() {
        return scoreRepository.findTop10ByOrderByPointsDesc();
    }

    public Score addScore(ScoreDto scoreDto) {
        Score score = new Score(scoreDto.getPlayerName(), scoreDto.getPoints());
        System.out.println("Saving score: " + score);
        return scoreRepository.save(score);
    }
}