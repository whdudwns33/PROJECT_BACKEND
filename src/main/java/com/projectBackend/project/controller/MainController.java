package com.projectBackend.project.controller;

import com.projectBackend.project.dto.MusicUserDto;
import com.projectBackend.project.service.MusicService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Setter
@Getter
@ToString
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
    private  final MusicService musicService;

    // 조영준
    // 판매순 정렬
    @GetMapping("/mainTop")
    public ResponseEntity<List<MusicUserDto>> mainTop () {
        return ResponseEntity.ok(musicService.musicSortList());
    }

    // 새로운 등록 순서
    @GetMapping("/newSong")
    public  ResponseEntity<List<MusicUserDto>> newList () {
        return ResponseEntity.ok(musicService.newSongList());
    }
}
