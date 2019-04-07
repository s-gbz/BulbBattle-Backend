package de.trzpiot.bulbbattle.service;

import de.trzpiot.bulbbattle.exception.GameIsRunningException;
import de.trzpiot.bulbbattle.exception.InvalidNumberOfRoundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameService {
    private final Environment environment;
    private final LightService lightService;
    private boolean running = false;

    @Autowired
    public GameService(Environment environment, LightService lightService) {
        this.environment = environment;
        this.lightService = lightService;
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
        final String bridgeIp = environment.getProperty("bridge.ip");
        final String bridgeUsername = environment.getProperty("bridge.username");
        final long lightId = Long.parseLong(Objects.requireNonNull(environment.getProperty("bridge.light.id")));
        int currentRound = 1;

        for (; currentRound <= rounds; currentRound++) {
            lightService.switchOn(bridgeIp, bridgeUsername, lightId);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lightService.switchOff(bridgeIp, bridgeUsername, lightId);
        }

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
