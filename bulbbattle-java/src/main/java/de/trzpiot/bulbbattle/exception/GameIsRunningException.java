package de.trzpiot.bulbbattle.exception;

public class GameIsRunningException extends RuntimeException {
    public GameIsRunningException() {
        super("Game is currently running.");
    }
}
