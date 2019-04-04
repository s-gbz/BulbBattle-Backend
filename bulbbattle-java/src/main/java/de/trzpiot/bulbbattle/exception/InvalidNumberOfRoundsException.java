package de.trzpiot.bulbbattle.exception;

public class InvalidNumberOfRoundsException extends RuntimeException {
    public InvalidNumberOfRoundsException() {
        super("The number of laps must be between 1 and 10.");
    }
}
