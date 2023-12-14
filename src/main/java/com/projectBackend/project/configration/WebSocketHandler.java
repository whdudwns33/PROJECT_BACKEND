package com.projectBackend.project.configration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectBackend.project.dto.CommentMessageDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    @Getter
    private final Map<String, WebSocketSession>  userSessionMap = new ConcurrentHashMap<>();
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("{}", payload);
        CommentMessageDTO commentMessage = objectMapper.readValue(payload, CommentMessageDTO.class);

        // 알림 메시지 생성
        Map<String, String> alertMessage = new HashMap<>();
        alertMessage.put("message", "새로운 댓글이 작성되었습니다: " + commentMessage.getCommentContent());

        // 게시글 작성자의 세션 가져오기
        WebSocketSession postAuthorSession = userSessionMap.get(commentMessage.getAuthorEmail());

        // 게시글 작성자에게 알림 메시지 보내기
        if (postAuthorSession != null && postAuthorSession.isOpen()) {
            postAuthorSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(alertMessage)));
        }
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String identifier = getEmailFromSession(session);
        if(identifier == null) {
            identifier = Objects.requireNonNull(session.getRemoteAddress()).getAddress().getHostAddress();
        }
        userSessionMap.put(identifier, session);
    }

    private String getEmailFromSession(WebSocketSession session) {
        try {
            Map<String, List<String>> headers = session.getHandshakeHeaders();
            List<String> emailList = headers.get("Email");
            if (emailList != null && !emailList.isEmpty()) {
                return emailList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
