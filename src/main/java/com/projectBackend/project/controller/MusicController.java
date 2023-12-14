package com.projectBackend.project.controller;


import com.projectBackend.project.dto.MusicDTO;
import com.projectBackend.project.entity.Music;
import com.projectBackend.project.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import static com.projectBackend.project.utils.Common.CORS_ORIGIN;


@Slf4j
//@CrossOrigin(origins = CORS_ORIGIN)
@RestController
@RequestMapping("/music")
@RequiredArgsConstructor
public class MusicController {
    private  final MusicService musicService;

//    @Autowired
//    public MusicController(MusicService musicService) {
//        this.musicService = musicService;
//    }

    //음악 리스트 조회
    @GetMapping("/musiclist")
    public ResponseEntity<List<MusicDTO>> musicList() {
        List<MusicDTO> list = musicService.getAllMusic();
        return ResponseEntity.ok(list);
    }

    //음악 상세 조회
    @GetMapping("detail/{id}")
    public ResponseEntity<MusicDTO> getMusicById(@PathVariable Long id) {
        MusicDTO music = musicService.getMusicById(id);
        if (music != null) {
            return ResponseEntity.ok(music);
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    //음악 검색
    @GetMapping("/search")
    public  ResponseEntity<List<MusicDTO>> searchMusic(@RequestParam String keyword) {
        List<MusicDTO> foundMusic = musicService.searchMusic(keyword);
        if (!foundMusic.isEmpty()) {
            return ResponseEntity.ok(foundMusic);
        }else  {
            return ResponseEntity.notFound().build();
        }
    }


    //음악 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> musicDelete(@PathVariable Long id) {
        boolean isTrue = musicService.deleteMusic(id);
        return ResponseEntity.ok(isTrue);
    }


    //음악 수정

    @PutMapping("/modify/{id}")
    public  ResponseEntity<Boolean> musicModify(@PathVariable Long id, @RequestBody MusicDTO musicDTO) {
        boolean isTrue = musicService.modifyMusic(id,musicDTO);
        return  ResponseEntity.ok(isTrue);

    }


    //음악 등록
    @PostMapping("/new")
    public ResponseEntity<MusicDTO> addMusic(@RequestBody MusicDTO musicDTO) {
        MusicDTO addedMusic = musicService.addMusic(musicDTO);
        return ResponseEntity.ok(addedMusic);
    }

    // 페이지네이션
    @GetMapping("/list/page")
    public ResponseEntity<List<MusicDTO>> musicList(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "11") int size) {
        List<MusicDTO> list = musicService.getMusicList(page, size);
        log.info("list : {}", list);
        return ResponseEntity.ok(list);
    }
    // 페이지 수 조회
    @GetMapping("/list/count")
    public ResponseEntity<Integer> musicPage(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        int count =  musicService.getMusicPage(pageRequest);
        return ResponseEntity.ok(count);
    }

}
