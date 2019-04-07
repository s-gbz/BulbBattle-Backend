package de.trzpiot.bulbbattle.service;

import de.trzpiot.bulbbattle.controller.WebSocketController;
import de.trzpiot.bulbbattle.exception.GameIsRunningException;
import de.trzpiot.bulbbattle.exception.InvalidNumberOfRoundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameService {
    private final WebSocketController socketController;
    private boolean running = false;

    @Autowired
    public GameService(WebSocketController socketController) {
        this.socketController = socketController;
    }

    public void start(int rounds) {
        if (rounds < 1 || rounds > 10) {
            throw new InvalidNumberOfRoundsException();
        }

        if (running) {
            throw new GameIsRunningException();
        } else {
            running = true;
            run(rounds);
        }
    }

    private void run(int rounds) {
        int currentRound = 1;
        socketController.sendColorCombinationToClient(getColorSequence(currentRound));

        /*
        nativeService.gameStart();

        while (currentRound <= rounds) {
            nativeService.roundStart(getColorSequence(currentRound),
                    getRoundDuration(currentRound));
            nativeService.roundPause(5000L);
            currentRound++;
        }

        nativeService.gamePause();
        running = false;
        */
    }

    private int[] getColorSequence(int currentRound) {
        int[] colorSequence = new int[currentRound * 3];

        for (int i = 0; i < currentRound * 3; i++) {
            colorSequence[i] = ThreadLocalRandom.current().nextInt(0, 4);
        }

        return colorSequence;
    }

    private Long getRoundDuration(Long currentRound) {
        return 2000L - (currentRound - 1) * 100;
    }
}
