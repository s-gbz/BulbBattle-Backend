package de.trzpiot.bulbbattle.exception;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String name) {
        super("User \""  + name + "\" is already registered.");
    }
}
