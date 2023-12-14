package com.projectBackend.project.dto;


import com.projectBackend.project.service.CommentService;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Getter
public class CommentResDTO {
    private String postId;
    private String nickName;
    private LocalDateTime regDate;

    private Set<WebSocketSession> sessions;

    @Builder
    public CommentResDTO(String postId, String nickName, LocalDateTime regDate){
        this.postId = postId;
        this.nickName = nickName;
        this.regDate = regDate;
        this.sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());

    }

    private <T> void sendMessage(T message, CommentService commentService) {
        for (WebSocketSession session : sessions) {
            try {
                commentService.sendMessage(session, message);
            } catch (Exception e) {
                log.error("Error sending message in ChatRoomResDto: ", e);
            }
        }
    }
}
