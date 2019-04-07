package de.trzpiot.bulbbattle.service;

import de.trzpiot.bulbbattle.controller.WebSocketController;
import de.trzpiot.bulbbattle.exception.GameIsRunningException;
import de.trzpiot.bulbbattle.exception.InvalidNumberOfRoundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameService {
    private final Environment environment;
    private final NativeService nativeService;
    private boolean running = false;

    @Autowired
    public GameService(Environment environment, NativeService nativeService) {
        this.environment = environment;
        this.nativeService = nativeService;
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
        String bridgeIp = environment.getProperty("bridge.ip");
        String bridgeUsername = environment.getProperty("bridge.username");
        long lightId = Long.parseLong(environment.getProperty("bridge.light.id"));
        int currentRound = 1;
        nativeService.gameStart(bridgeIp, bridgeUsername, lightId, 10000000L);

        while (currentRound <= rounds) {
            nativeService.roundStart(bridgeIp, bridgeUsername, lightId, getColorSequence(currentRound), getRoundDuration(currentRound));
            nativeService.roundPause(bridgeIp, bridgeUsername, lightId, 12000000L);
            currentRound++;
        }

        nativeService.gamePause(bridgeIp, bridgeUsername, lightId);
        running = false;
    }

    private int[] getColorSequence(int currentRound) {
        int[] colorSequence = new int[currentRound * 3];

        for (int i = 0; i < currentRound * 3; i++) {
            colorSequence[i] = ThreadLocalRandom.current().nextInt(0, 4);
        }

        return colorSequence;
    }

    private Long getRoundDuration(int currentRound) {
        return 1500000L - (currentRound - 1) * 100000;
    }
}
