package com.projectBackend.project.service;

import com.projectBackend.project.dto.MusicDTO;
import com.projectBackend.project.entity.Music;
import com.projectBackend.project.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MusicService {
    private final MusicRepository musicRepository;

    @Autowired

    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }


    //음악 전체 조회
    public List<MusicDTO> getAllMusic() {
        List<Music> musics = musicRepository.findAll();
        List<MusicDTO> musicDTOS = new ArrayList<>();
        for (Music music : musics) {
            musicDTOS.add(convertEntityToDto(music));
        }
        return musicDTOS;
    }

    //상세 조회
    public MusicDTO getMusicById(Long id) {
        Optional<Music> musicOptional = musicRepository.findById(id);
        if (musicOptional.isPresent()) {
            Music music = musicOptional.get();
            return convertEntityToDto(music);
        } else {
            return null;
        }
    }

    //음악 검색
    public List<MusicDTO> searchMusic(String keyword) {
        List<Music> foundMusics = musicRepository.findByMusicTitleContainingIgnoreCase(keyword);
        List<MusicDTO> musicDTOS = new ArrayList<>();
        for (Music music : foundMusics) {
            musicDTOS.add(convertEntityToDto(music));
        }
        return musicDTOS;
    }


    //음악 삭제
    public boolean deleteMusic(Long id) {
        Optional<Music> musicOptional = musicRepository.findById(id);
        if (musicOptional.isPresent()) {
            musicRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    //음악 수정
    public boolean modifyMusic(Long id, MusicDTO musicDTO) {
        try {
            Music music = musicRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 음원이 존재하지 않습니다.")
            );
            music.setMusicTitle(musicDTO.getMusicTitle());
            music.setArtistName(musicDTO.getArtistName());
            music.setLyricist(musicDTO.getLyricist());
            music.setComposer(musicDTO.getComposer());
            music.setGenre(musicDTO.getGenre());
            music.setPurchaseCount(musicDTO.getPurchaseCount());
            music.setHeartCount(musicDTO.getHeartCount());
            music.setLyrics(musicDTO.getLyrics());
            music.setReleaseDate(musicDTO.getReleaseDate());
            music.setThumbnailImage(musicDTO.getThumbnailImage());
            music.setPromoImage(musicDTO.getPromoImage());
            musicRepository.save(music);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public MusicDTO addMusic(MusicDTO musicDTO) {
        Music music = convertDtoToEntity(musicDTO);
        Music savedMusic = musicRepository.save(music);
        return convertEntityToDto(savedMusic);
    }

    private Music convertDtoToEntity(MusicDTO musicDTO) {
        Music music = new Music();
        music.setMusicTitle(musicDTO.getMusicTitle());
        music.setArtistName(musicDTO.getArtistName());
        music.setLyricist(musicDTO.getLyricist());
        music.setComposer(musicDTO.getComposer());
        music.setGenre(musicDTO.getGenre());
        music.setPurchaseCount(musicDTO.getPurchaseCount());
        music.setHeartCount(musicDTO.getHeartCount());
        music.setLyrics(musicDTO.getLyrics());
        music.setReleaseDate(musicDTO.getReleaseDate());
        music.setThumbnailImage(musicDTO.getThumbnailImage());
        music.setPromoImage(musicDTO.getPromoImage());
        return music;

    }



    private MusicDTO convertEntityToDto(Music music) {
        MusicDTO musicDTO = new MusicDTO();
        musicDTO.setMusicTitle(music.getMusicTitle());
        musicDTO.setComposer(music.getComposer());
        musicDTO.setArtistName(music.getArtistName());
        musicDTO.setLyricist(music.getLyricist());
        musicDTO.setGenre(music.getGenre());
        musicDTO.setPurchaseCount(music.getPurchaseCount());
        musicDTO.setHeartCount(music.getHeartCount());
        musicDTO.setLyrics(music.getLyrics());
        musicDTO.setReleaseDate(music.getReleaseDate());
        musicDTO.setThumbnailImage(music.getThumbnailImage());
        musicDTO.setPromoImage(music.getPromoImage());
        return musicDTO;
    }
}