package com.projectBackend.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectBackend.project.configration.WebSocketHandler;
import com.projectBackend.project.dto.CommentDTO;
import com.projectBackend.project.entity.Comment;
import com.projectBackend.project.entity.Community;
import com.projectBackend.project.entity.Member;
import com.projectBackend.project.repository.CommentRepository;
import com.projectBackend.project.repository.CommunityRepository;
import com.projectBackend.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final ObjectMapper objectMapper;
//    private Map<String, ChatRoomResDTO> chatRooms;

    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;
    private final UserRepository memberRepository;
    private final WebSocketHandler webSocketHandler;

    // 댓글 등록
    public boolean commentRegister(CommentDTO commentDTO){
        try {
            Comment comment = new Comment();
            Community community = communityRepository.findById(commentDTO.getCommunityId()).orElseThrow(
                    ()-> new RuntimeException("해당 게시글이 존재하지 않습니다.")
            );
            if(commentDTO.getEmail() != null && !commentDTO.getEmail().isEmpty()){
                Member member = memberRepository.findByUserEmail(commentDTO.getEmail()).orElse(null);
                if(member != null) { // 회원이 존재하는 경우
                    comment.setMember(member);
                } else {
                    comment.setNickName(commentDTO.getNickName());
                    comment.setPassword(commentDTO.getPassword());
                }
            } else { // 이메일이 null이거나 빈 문자열인 경우
                comment.setNickName(commentDTO.getNickName());
                comment.setPassword(commentDTO.getPassword());
            }
            Comment parentComment = null;
            if (commentDTO.getParentCommentId() != null) {
                parentComment = commentRepository.findById(commentDTO.getParentCommentId()).orElseThrow(
                        () -> new RuntimeException("해당 부모 댓글이 존재하지 않습니다.")
                );
            }
            comment.setContent(commentDTO.getContent());
            comment.setParentComment(parentComment);
            comment.setCommunity(community);
            commentRepository.save(comment);

            // 댓글 등록 후 알림 메시지 전송
            String postEmail = community.getMember().getUserEmail();
            String postIpAddress = community.getIpAddress(); // 게시글 작성자의 IP 주소
            WebSocketSession postAuthorSession = webSocketHandler.getUserSessionMap().get(
                    postEmail != null ? postEmail : postIpAddress // 이메일이 없는 경우 IP 주소를 사용합니다.
            );
            if (postAuthorSession != null && postAuthorSession.isOpen()) { // 세션이 열려있는 경우에만 메시지를 보냅니다.
                String message = "새로운 댓글이 작성되었습니다: " + comment.getContent();
                postAuthorSession.sendMessage(new TextMessage(message)); // 알림 메시지를 보냅니다.
            }
            return true;

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public <T> void sendMessage(WebSocketSession session, T message){
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
    // 댓글 수정
    public boolean commentModify(CommentDTO commentDto) {
        try {
            Comment comment = commentRepository.findById(commentDto.getCommentId()).orElseThrow(
                    () -> new RuntimeException("해당 댓글이 존재하지 않습니다.")
            );
            comment.setContent(commentDto.getContent());
            commentRepository.save(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 댓글 삭제
    public boolean commentDelete(Long commentId) {
        try {
            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new RuntimeException("해당 댓글이 존재하지 않습니다.")
            );
            commentRepository.delete(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 댓글 목록 조회
    public List<CommentDTO> getCommentList(Long communityId) {
        try {
            Community community = communityRepository.findById(communityId).orElseThrow(
                    () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
            );
            List<Comment> comments = commentRepository.findByCommunity(community);
            List<CommentDTO> commentDtos = new ArrayList<>();
            for (Comment comment : comments) {
                commentDtos.add(convertEntityToDto(comment));
            }
            return commentDtos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Page<CommentDTO> getCommentListPage(Long communityId, Pageable pageable) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
        Page<Comment> comments = commentRepository.findByCommunity(community, pageable);
        return comments.map(this::convertEntityToDto);
    }
    // 댓글 검색
    public List<CommentDTO> getCommentList(String keyword) {
        List<Comment> comments = commentRepository.findByContentContaining(keyword);
        List<CommentDTO> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(convertEntityToDto(comment));
        }
        return commentDtos;
    }

    // 댓글 엔티티를 DTO로 변환
    private CommentDTO convertEntityToDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentId(comment.getCommentId());
        commentDTO.setCommunityId(comment.getCommunity().getCommunityId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setRegDate(comment.getRegDate());
        if (comment.getMember() != null) { // 회원이 존재하는 경우
            commentDTO.setEmail(comment.getMember().getUserEmail());
        } else { // 회원이 존재하지 않는 경우
            commentDTO.setEmail(comment.getNickName()); // 닉네임을 이메일 필드에 설정
            commentDTO.setPassword(comment.getPassword());
        }
        List<CommentDTO> childComments = new ArrayList<>();
        for (Comment childComment : comment.getChildComments()) {
            childComments.add(convertEntityToDto(childComment));
        }
        commentDTO.setChildComments(childComments);
        return commentDTO;
    }
}
