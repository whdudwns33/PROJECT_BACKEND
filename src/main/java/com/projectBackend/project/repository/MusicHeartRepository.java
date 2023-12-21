package com.projectBackend.project.repository;

import com.projectBackend.project.entity.MusicHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MusicHeartRepository extends JpaRepository<MusicHeart, Long> {
    List<MusicHeart> findByMusic_MusicId(Long musicId);
//    MusicHeart findByMusicIdAndUserId(Long musicId, Long userId);


    @Transactional
    @Modifying
    @Query("DELETE FROM MusicHeart mh WHERE mh.music.id = :musicId AND mh.member.id = :userId")
//    MusicHeart  findByMusic_MusicIdAndMember_UserId(Long musicId, Long userId);
    void deleteByMusic_MusicIdAndMember_UserId(Long musicId, Long userId);

}