package org.example.gitmazonkafkaconsumerserver.services;

import org.example.gitmazonkafkaconsumerserver.WebSocketHandler;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private final WebSocketHandler webSocketHandler;

    public WebSocketService(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    public void sendMessageToUser(String username, String message) {
        try {
            webSocketHandler.sendMessageToUser(username, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
