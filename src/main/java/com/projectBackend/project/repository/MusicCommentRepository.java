//package com.projectBackend.project.repository;
//
//import com.projectBackend.project.entity.MusicComment;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface MusicCommentRepository extends JpaRepository<MusicComment, Long> {
//    List<MusicComment> findByCommentContentContaining(String keyword);
//
//    List<MusicComment>findByMusicId(Long musicId);
//}
