package org.example.gitmazonkafkaconsumerserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LogManager.getLogger(WebSocketHandler.class);

//    private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    private Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String username = session.getUri().getQuery().split("=")[1];
        userSessions.put(username, session);

//        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        log.info("Connection closed");
        userSessions.values().remove(session);
    }

    public void sendMessageToUser(String username, String message) throws Exception {
        WebSocketSession session = userSessions.get(username);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }
}
