package com.opensw.master.global.websocket.presentation;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import com.opensw.master.domain.account.domain.Account;
import com.opensw.master.domain.account.application.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ReplicaServerHandler extends TextWebSocketHandler {

    @Autowired
    AccountService accountService;

    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : " + message.getPayload());

        Integer number = Integer.valueOf(payload);
        List<Account> listAccount = this.accountService.getLastIds(number);
        log.info("payload : " + listAccount);

        Gson gson = new Gson();
        for(WebSocketSession sess: list) {
            for (Account account: listAccount) {
                TextMessage sendMsg = new TextMessage(gson.toJson(account));
                sess.sendMessage(sendMsg);
            }
        }
    }

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
        log.info(session + " Client Connection");
    }

    /* Client가 접속 해제 시 호출되는 메서드 */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + " Client Disconnection");
        list.remove(session);
    }
}
