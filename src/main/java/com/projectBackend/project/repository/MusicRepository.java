package com.projectBackend.project.repository;

import com.projectBackend.project.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {
    List<Music> findByMusicTitleContainingIgnoreCase(String keyword);
}
