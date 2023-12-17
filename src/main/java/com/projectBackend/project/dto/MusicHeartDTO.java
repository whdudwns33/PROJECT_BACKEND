//package com.projectBackend.project.dto;
//
//import com.projectBackend.project.entity.MusicHeart;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//
//
//public class MusicHeartDTO {
//
//    private String musicHeartId;  //음악 좋아요 ID
//    private int heartCount; // 좋아요 수
//    private LocalDateTime heartDate; // 좋아요 날짜
//    private String userEmail; // 회원 이메일
//    private Long musicId; // 음악 ID
//
//
//    // MusicHeart 엔티티를 MusicHeartDTO로 변환하는 메서드
//    public static MusicHeartDTO of (MusicHeart musicHeart) {
//        MusicHeartDTO dto = new MusicHeartDTO();
//
//        dto.setMusicHeartId(musicHeart.getMusicHeartId()); // 음악 좋아요 ID 설정
//        dto.setHeartCount(musicHeart.getHeartCount()); // 좋아요 수 설정
//        dto.setHeartDate(musicHeart.getHeartDate()); // 좋아요 날짜 설정
//
//        //MusicHeart에 Member가 존재한다면, 회원 이메일 설정
//        if (musicHeart.getMember() != null) {
//            dto.setUserEmail(musicHeart.getMember().getUserEmail());
//        }
//
//        //MusicHeart에 Music이 존재한다면, 음악 ID 설정
//        if (musicHeart.getMusic() != null) {
//            dto.setMusicId(musicHeart.getMusic().getMusicId());
//        }
//
//        return dto;
//
//    }
//
//}
