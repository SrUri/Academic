package com.example.Practica2DAAW;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
@CrossOrigin(origins = "http://10.112.159.250:5173")

public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    public List<Score> getAllScores() {
        return scoreService.getTopScores();
    }

    @PostMapping
    public ResponseEntity<Score> submitScore(@RequestBody ScoreDto scoreDto) {
        System.out.println("Received score submission: " + scoreDto.getPlayerName() + " with points: " + scoreDto.getPoints());
        Score score = scoreService.addScore(scoreDto);
        return ResponseEntity.ok(score);
    }
}