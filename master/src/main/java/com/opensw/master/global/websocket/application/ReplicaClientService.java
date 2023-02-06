package com.opensw.master.global.websocket.application;

import com.opensw.master.global.common.MasterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ReplicaClientService {

    @Autowired
    MasterProperties masterProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReplicaClientService.class);

    @Profile("slave")
    @PostConstruct
    public void connect() {
        LOGGER.info("[WebSocket Server]" + masterProperties.getUri());

        try {
            WebSocketClient webSocketClient = new StandardWebSocketClient();

            WebSocketSession webSocketSession = webSocketClient.doHandshake(new TextWebSocketHandler() {
                @Override
                public void handleTextMessage(WebSocketSession session, TextMessage message) {
                    LOGGER.info("received message - " + message.getPayload());
                }

                @Override
                public void afterConnectionEstablished(WebSocketSession session) {
                    LOGGER.info("established connection - " + session);
                }
            }, new WebSocketHttpHeaders(), URI.create(masterProperties.getUri())).get();

            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
                try {
                    TextMessage message = new TextMessage("Hello !!");
                    webSocketSession.sendMessage(message);
                    LOGGER.info("sent message - " + message.getPayload());
                } catch (Exception e) {
                    LOGGER.error("Exception while sending a message", e);
                }
            }, 1, 10, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error("Exception while accessing websockets", e);
        }
    }
}
