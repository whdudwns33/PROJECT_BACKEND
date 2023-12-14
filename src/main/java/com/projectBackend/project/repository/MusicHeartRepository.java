package com.projectBackend.project.repository;

import com.projectBackend.project.entity.Member;
import com.projectBackend.project.entity.Music;
import com.projectBackend.project.entity.MusicHeart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicHeartRepository  extends JpaRepository<MusicHeart, Long> {
    MusicHeart findByMemberAndMusic(Member member, Music music);
    boolean existsByMemberAndMusic(Member member, Music music);

}
