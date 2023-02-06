package com.opensw.master.global.websocket.application;

import com.google.gson.Gson;
import com.opensw.master.domain.account.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opensw.master.global.common.MasterProperties;
import com.opensw.master.domain.account.application.AccountService;
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
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Profile("slave")
public class ReplicaClientService {

    @Autowired
    MasterProperties masterProperties;

    @Autowired
    AccountService accountService;

    @PostConstruct
    public void connect() {
        log.info("[WebSocket Server] : " + masterProperties.getUri());

        try {
            WebSocketClient webSocketClient = new StandardWebSocketClient();

            WebSocketSession webSocketSession = webSocketClient.doHandshake(new TextWebSocketHandler() {
                @Override
                public void handleTextMessage(WebSocketSession session, TextMessage message) {
                    String payload = message.getPayload();
                    log.info("received message - " + payload);
                    try {
                        saveReplica(payload);
                    } catch (Exception e) {
                        log.error("Exception SQL replica message", e);
                    }
                }

                @Override
                public void afterConnectionEstablished(WebSocketSession session) {
                    log.info("established connection - " + session);
                }
            }, new WebSocketHttpHeaders(), URI.create(masterProperties.getUri())).get();

            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
                try {
                    TextMessage message = new TextMessage(getLastId());
                    webSocketSession.sendMessage(message);
                    log.info("sent message - " + message.getPayload());
                } catch (Exception e) {
                    log.error("Exception while sending a message", e);
                }
            }, 0, 1, TimeUnit.SECONDS);    // 1초 뒤 바로 실행
        } catch (Exception e) {
            log.error("Exception while accessing websockets", e);
        }
    }

    public String getLastId() {
        Optional<Account> optionalAccount = this.accountService.getLastId();
        if (!optionalAccount.isPresent()) {
            return "0";
        }
        Account account = optionalAccount.get();
        return String.valueOf(account.getId());
    }

    public void saveReplica(String payload) {
        Gson gson = new Gson();
        Account account = gson.fromJson(payload, Account.class);
        log.info("id : " + account.getId());
        log.info("username : " + account.getUsername());
        log.info("email : " + account.getEmail());
        log.info("phone : " + account.getPhone());
        log.info("createAt : " + account.getCreatedAt());
        log.info("updateAt : " + account.getUpdatedAt());
        this.accountService.saveReplica(account);
    }
}
