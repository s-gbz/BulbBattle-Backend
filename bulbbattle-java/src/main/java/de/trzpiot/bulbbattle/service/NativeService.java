package de.trzpiot.bulbbattle.service;

import org.springframework.stereotype.Service;

@Service
public class NativeService {
    static {
        System.loadLibrary("bulbbattle");
    }

    public native void roundPause(String bridgeIp, String bridgeUsername, long lightId, long duration);
    public native void roundStart(String bridgeIp, String bridgeUsername, long lightId, int[] colorSequence, long duration);
    public native void gamePause(String bridgeIp, String bridgeUsername, long lightId, long duration);
    public native void gameStart(String bridgeIp, String bridgeUsername, long lightId);
}
