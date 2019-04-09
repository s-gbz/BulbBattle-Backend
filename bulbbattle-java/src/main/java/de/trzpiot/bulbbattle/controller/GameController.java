package de.trzpiot.bulbbattle.controller;

import de.trzpiot.bulbbattle.model.StartModel;
import de.trzpiot.bulbbattle.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public void start(@RequestBody StartModel model) {
        gameService.start(model.getNumberOfRounds());
    }
}
