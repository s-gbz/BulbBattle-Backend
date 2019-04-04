package de.trzpiot.bulbbattle.service;

import de.trzpiot.bulbbattle.exception.GameIsRunningException;
import de.trzpiot.bulbbattle.exception.InvalidNumberOfRoundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameService {
    private final NativeService nativeService;
    private boolean running = false;

    @Autowired
    public GameService(NativeService nativeService) {
        this.nativeService = nativeService;
    }

    public void start(Long rounds) {
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

    private void run(Long rounds) {
        long currentRound = 1;
        nativeService.gameStart();

        while (currentRound <= rounds) {
            nativeService.roundStart(getColorSequence(currentRound),
                    getRoundDuration(currentRound));
            nativeService.roundPause(5000L);
            currentRound++;
        }

        nativeService.gamePause();
        running = false;
    }

    private String getColorSequence(Long currentRound) {
        StringBuilder colorSequence = new StringBuilder();

        for (int i = 0; i < currentRound * 3; i++) {
            colorSequence.append(ThreadLocalRandom.current().nextInt(0, 4));
        }

        return colorSequence.toString();
    }

    private Long getRoundDuration(Long currentRound) {
        return 2000L - (currentRound - 1) * 100;
    }
}
