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
    private final String BRIDGE_IP;
    private final String BRIDGE_USERNAME;
    private final long LIGHT_ID;
    private final long REQUIRED_TIME_OF_LIGHT_TO_CHANGE_STATUS = 2;
    private boolean running = false;

    @Autowired
    public GameService(Environment environment, LightService lightService) {
        this.environment = environment;
        this.lightService = lightService;

        BRIDGE_IP = environment.getProperty("bridge.ip");
        BRIDGE_USERNAME = environment.getProperty("bridge.username");
        LIGHT_ID = Long.parseLong(Objects.requireNonNull(environment.getProperty("bridge.light.id")));
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
        int currentRound = 1;

        startGame();
        TimeUnit.SECONDS.sleep(10 + REQUIRED_TIME_OF_LIGHT_TO_CHANGE_STATUS);

        for (; currentRound <= rounds; currentRound++) {
            int[] colorSequence = getColorSequence(currentRound);

            for (int colorCode : colorSequence) {
                lightOn();
                changeColor(colorCode);
                TimeUnit.MILLISECONDS.sleep(getRoundDuration(currentRound));
                lightOff();
            }

            endRound();
            TimeUnit.SECONDS.sleep(15 + REQUIRED_TIME_OF_LIGHT_TO_CHANGE_STATUS);
        }

        endGame();
        running = false;
    }

    private void startGame() {
        lightService.switchOn(BRIDGE_IP, BRIDGE_USERNAME, LIGHT_ID);
        lightService.setColor(BRIDGE_IP, BRIDGE_USERNAME, LIGHT_ID, 255, 255, 255);
        lightService.setBrightness(BRIDGE_IP, BRIDGE_USERNAME, LIGHT_ID, 255);
    }

    private void endGame() {
        lightService.switchOff(BRIDGE_IP, BRIDGE_USERNAME, LIGHT_ID);
    }

    private void endRound() {
        lightService.setColor(BRIDGE_IP, BRIDGE_USERNAME, LIGHT_ID, 255, 255, 255);
        lightService.setBrightness(BRIDGE_IP, BRIDGE_USERNAME, LIGHT_ID, 255);
    }

    private void changeColor(int colorCode) {
        switch (colorCode) {
            case 0:
                lightService.setColor(BRIDGE_IP, BRIDGE_USERNAME, LIGHT_ID, 244, 67, 54);
                break;
            case 1:
                lightService.setColor(BRIDGE_IP, BRIDGE_USERNAME, LIGHT_ID, 76, 175, 80);
                break;
            case 2:
                lightService.setColor(BRIDGE_IP, BRIDGE_USERNAME, LIGHT_ID, 33, 150, 243);
                break;
            case 3:
                lightService.setColor(BRIDGE_IP, BRIDGE_USERNAME, LIGHT_ID, 255, 235, 59);
                break;
            default:
                lightService.setColor(BRIDGE_IP, BRIDGE_USERNAME, LIGHT_ID, 255, 255, 255);
                break;
        }
    }

    private void lightOn() {
        lightService.switchOn(BRIDGE_IP, BRIDGE_USERNAME, LIGHT_ID);
    }

    private void lightOff() {
        lightService.switchOff(BRIDGE_IP, BRIDGE_USERNAME, LIGHT_ID);
    }

    private int[] getColorSequence(int currentRound) {
        int[] colorSequence = new int[currentRound * 3];

        for (int i = 0; i < currentRound * 3; i++) {
            colorSequence[i] = ThreadLocalRandom.current().nextInt(0, 4);
        }

        return colorSequence;
    }

    private Long getRoundDuration(int currentRound) {
        return 1500L - (currentRound - 1) * 100;
    }
}
