package de.trzpiot.bulbbattle.service;

import org.springframework.stereotype.Service;

@Service
public class NativeService {
    static {
        System.loadLibrary("bulbbattle");
    }

    public native void roundPause(Long duration);
    public native void roundStart(String colorSequence, Long duration);
    public native void gamePause();
    public native void gameStart();
}
