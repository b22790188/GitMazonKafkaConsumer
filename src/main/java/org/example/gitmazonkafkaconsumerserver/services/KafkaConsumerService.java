package org.example.gitmazonkafkaconsumerserver.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class KafkaConsumerService {

    private WebSocketService webSocketService;

    public KafkaConsumerService(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @KafkaListener(topics = "container_stats", groupId = "container-group")
    public void listen(String message) {
        log.info(message);

        webSocketService.sendMessageToAllConnectedClients(message);
    }
}
