package de.trzpiot.bulbbattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendColorCombinationToClient(int[] colorCombination) {
        messagingTemplate.convertAndSend("/update-color-combination", colorCombination);
    }

    public void sendGameStateToClient(boolean gameState) {
        messagingTemplate.convertAndSend("/update-game-state", gameState);
    }
}
