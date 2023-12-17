//package com.projectBackend.project.dto;
//
//
//import com.projectBackend.project.entity.MusicComment;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//
//public class MusicCommentDTO {
//
//    private String musicCommentId; // 음악 댓글 ID
//    private String commentContent;   // 댓글 내용
//    private LocalDateTime commentDate; // 댓글 작성 일자
//    private String userEmail;   // 회원 이메일
//
//
//    // MusicComment 엔티티를 MusicCommentDTO로 변환하는 메서드
//    public static MusicCommentDTO of(MusicComment musicComment) {
//        MusicCommentDTO dto = new MusicCommentDTO();
//        dto.setMusicCommentId(musicComment.getMusicCommentID()); // 음악 댓글 id
//        dto.setCommentContent(musicComment.getCommentContent()); // 댓글 내용 설정
//        dto.setCommentDate(musicComment.getCommentDate()); // 댓글 작성 일자 설정
//
//        // 만약 MusicComment에 Member가 존재한다면, 회원의 이메일을 설정
//        if (musicComment.getMember() != null) {
//            dto.setUserEmail(musicComment.getMember().getUserEmail());
//        }
//        return dto;
//    }
//
//}
