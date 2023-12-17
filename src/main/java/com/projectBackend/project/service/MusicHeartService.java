//package com.projectBackend.project.service;
//
//
//import com.projectBackend.project.entity.Member;
//import com.projectBackend.project.entity.Music;
//import com.projectBackend.project.entity.MusicHeart;
//import com.projectBackend.project.repository.MusicHeartRepository;
//import com.projectBackend.project.repository.MusicRepository;
//import com.projectBackend.project.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class MusicHeartService {
//
//    private final MusicHeartRepository musicHeartRepository;
//    private final UserRepository userRepository;
//    private final MusicRepository musicRepository;
//
//    @Autowired
//    public MusicHeartService(MusicHeartRepository musicHeartRepository, UserRepository userRepository, MusicRepository musicRepository) {
//        this.musicHeartRepository = musicHeartRepository;
//        this.userRepository = userRepository;
//        this.musicRepository = musicRepository;
//    }
//
//    //사용자가 해당 음악을 이미 좋아요 했는지 체크하는 메서드
//
//    public boolean beforeLikeMusic(String userEmail, Long musicId) {
//        Optional<Member> optionalMember = userRepository.findByUserEmail(userEmail);
//        Member member = optionalMember.orElse(null);
//        // 사용자 이메일로 회원 정보 조회
//
//
//        Optional<Music> optionalMusic = musicRepository.findById(musicId);
//        Music music = optionalMusic.orElse(null);
//        // 음악 ID로 음악 정보 조회
//
//        // 회원 정보와 음악 정보가 모두 존재하면 좋아요 여부 확인
//        if (member !=null && music !=null) {
//            return musicHeartRepository.existsByMemberAndMusic(member,music);
//        }
//
//        return false;  // 회원 정보 또는 음악 정보가 없는 경우
//    }
//
//
//
//    // 좋아요 추가
//    public boolean addMusicHeart(String userEmail, Long musicId) {
//        Optional<Member> optionalMember = userRepository.findByUserEmail(userEmail);
//        Member member = optionalMember.orElse(null);
//        // 사용자 이메일로 회원 정보 조회
//
//        Optional<Music> optionalMusic = musicRepository.findById(musicId);
//        Music music = optionalMusic.orElse(null);
//        // 음악 ID로 음악 정보 조회
//
//        if (member != null && music != null) {
//            // 사용자가 해당 음악을 이미 좋아요 했는지 확인
//            if (!beforeLikeMusic(userEmail, musicId)) {
//                MusicHeart musicHeart = new MusicHeart();
//                musicHeart.setMember(member);
//                musicHeart.setMusic(music);
//                musicHeartRepository.save(musicHeart);
//                return true; // 좋아요 추가 성공
//            }
//        }
//        return false;
//        // 이미 좋아요를 누른 경우 또는 회원 정보나 음악 정보가 잘못된 경우
//    }
//
//
//    // 좋아요 제거하기.
//    public boolean removeMusicHeart(String userEmail, Long musicId) {
//        Optional<Member> optionalMember = userRepository.findByUserEmail(userEmail);
//        Member member = optionalMember.orElse(null);
//        // 사용자 이메일로 회원 정보 조회
//
//        Optional<Music> optionalMusic = musicRepository.findById(musicId);
//        Music music = optionalMusic.orElse(null);
//        // 음악 ID로 음악 정보 조회
//
//        if (member != null && music != null) {
//            if (beforeLikeMusic(userEmail, musicId)) {
//                MusicHeart musicHeart = musicHeartRepository.findByMemberAndMusic(member, music);
//                musicHeartRepository.delete(musicHeart);
//                return true; // 좋아요 제거 성공
//            }
//        }
//        return false; // 좋아요를 누른 적이 없거나 회원 정보나 음악 정보가 잘못된 경우
//    }
//
//}

