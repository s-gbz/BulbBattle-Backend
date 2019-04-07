package de.trzpiot.bulbbattle.exception;

public class GameUnexpectedlyClosedException extends RuntimeException {
    public GameUnexpectedlyClosedException(String message) {
        super(message);
    }
}
