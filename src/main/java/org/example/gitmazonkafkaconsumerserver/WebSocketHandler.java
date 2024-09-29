package org.example.gitmazonkafkaconsumerserver;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private Set<WebSocketSession> adminSessions = ConcurrentHashMap.newKeySet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String username = session.getUri().getQuery().split("=")[1];
        if ("admin".equals(username)) {
            adminSessions.add(session);
        } else {
            userSessions.put(username, session);
        }

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

    public void sendMessageToAdmins(String message) throws Exception {
        for (WebSocketSession session : adminSessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }
}
