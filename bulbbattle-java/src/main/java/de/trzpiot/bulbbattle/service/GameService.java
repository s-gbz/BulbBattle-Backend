package de.trzpiot.bulbbattle.service;

import de.trzpiot.bulbbattle.exception.GameIsRunningException;
import de.trzpiot.bulbbattle.exception.GameUnexpectedlyClosedException;
import de.trzpiot.bulbbattle.exception.InvalidNumberOfRoundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

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
            try {
                run(rounds);
            } catch (InterruptedException e) {
                throw new GameUnexpectedlyClosedException("An error occurred while waiting for a thread.");
            }
        }
    }

    private void run(int rounds) throws InterruptedException {
        final String bridgeIp = environment.getProperty("bridge.ip");
        final String bridgeUsername = environment.getProperty("bridge.username");
        final long lightId = Long.parseLong(Objects.requireNonNull(environment.getProperty("bridge.light.id")));
        int currentRound = 1;

        for (; currentRound <= rounds; currentRound++) {
            lightService.setBrightness(bridgeIp, bridgeUsername, lightId, 255);
            lightService.switchOn(bridgeIp, bridgeUsername, lightId);
            TimeUnit.SECONDS.sleep(1);
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
