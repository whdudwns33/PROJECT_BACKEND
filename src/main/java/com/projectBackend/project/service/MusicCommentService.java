//package com.projectBackend.project.service;
//
//import com.projectBackend.project.dto.MusicCommentDTO;
//import com.projectBackend.project.dto.MusicHeartDTO;
//import com.projectBackend.project.entity.Member;
//import com.projectBackend.project.entity.Music;
//import com.projectBackend.project.entity.MusicComment;
//import com.projectBackend.project.repository.MusicCommentRepository;
//import com.projectBackend.project.repository.MusicRepository;
//import com.projectBackend.project.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//
//@Service
//@RequiredArgsConstructor
//
//public class MusicCommentService {
//    private final MusicCommentRepository musicCommentRepository;
//    private final MusicRepository musicRepository;
//    private final UserRepository userRepository;
//
//    //댓글 등록
//
//    public boolean registerMusicComment(MusicCommentDTO musicCommentDTO) {
//        try {
//            MusicComment musicComment = new MusicComment();
//            musicComment.setMusicCommentID(musicCommentDTO.getMusicCommentId());
//            musicComment.setCommentContent(musicCommentDTO.getCommentContent());
//            musicComment.setCommentDate(musicCommentDTO.getCommentDate());
//
//            // 회원 이메일로 회원 조회 후 댓글의 회원 정보로 설정
//            Optional<Member> memberOptional = userRepository.findByUserEmail(musicCommentDTO.getUserEmail());
//            Member member = memberOptional.orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
//            musicComment.setMember(member);
//
//            // 댓글 등록.
//            musicCommentRepository.save(musicComment);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    //  댓글 수정
//    public boolean modifyMusicComment(MusicCommentDTO musicCommentDTO) {
//        try {
//            // 'musicCommentId'를 Long 타입으로 변환
//            Long commentId = Long.parseLong(musicCommentDTO.getMusicCommentId());
//
//            // 댓글 ID로 댓글 조회
//            MusicComment musicComment = musicCommentRepository.findById(commentId)
//                    .orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));
//
//            // 댓글 내용 수정
//            musicComment.setCommentContent(musicCommentDTO.getCommentContent());
//
//            // 수정한 댓글 내용 저장
//            musicCommentRepository.save(musicComment);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    //  댓글 삭제
//    public boolean deleteMusicComment(Long musicCommentId) {
//        try {
//            musicCommentRepository.deleteById(musicCommentId);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // 모든 음악 댓글 조회
//    public List<MusicCommentDTO> getAllMusicComments() {
//        try {
//            List<MusicComment> musicComments = musicCommentRepository.findAll();
//            List<MusicCommentDTO> musicCommentDTOs = new ArrayList<>();
//
//            for (MusicComment musicComment : musicComments) {
//                musicCommentDTOs.add(convertEntityToDTO(musicComment));
//            }
//
//            return musicCommentDTOs;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // 특정 ID의 음악 댓글 조회
//    public List<MusicCommentDTO> getMusicCommentsByMusicId(Long musicId) {
//        try {
//            List<MusicComment> musicComments = musicCommentRepository.findByMusicId(musicId);
//            List<MusicCommentDTO> musicCommentDTOs = new ArrayList<>();
//
//            for (MusicComment musicComment : musicComments) {
//                musicCommentDTOs.add(convertEntityToDTO(musicComment));
//            }
//
//            return musicCommentDTOs;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//
//    // 특정 키워드를 포함한 음악 댓글 검색
//    public List<MusicCommentDTO> searchMusicComments(String keyword) {
//        try {
//            List<MusicComment> musicComments = musicCommentRepository.findByCommentContentContaining(keyword);
//            List<MusicCommentDTO> musicCommentDTOs = new ArrayList<>();
//
//            for (MusicComment musicComment : musicComments) {
//                musicCommentDTOs.add(convertEntityToDTO(musicComment));
//            }
//
//            return musicCommentDTOs;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//
//    // Entity를 DTO로 변환하는 메서드
//    private MusicCommentDTO convertEntityToDTO(MusicComment musicComment) {
//        MusicCommentDTO musicCommentDTO = new MusicCommentDTO();
//        musicCommentDTO.setMusicCommentId(musicComment.getMusicCommentID());
//        musicCommentDTO.setCommentContent(musicComment.getCommentContent());
//        musicCommentDTO.setCommentDate(musicComment.getCommentDate());
//        musicCommentDTO.setUserEmail(musicComment.getMember().getUserEmail());
//        return musicCommentDTO;
//    }
//}
