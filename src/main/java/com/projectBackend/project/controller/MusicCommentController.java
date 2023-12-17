//package com.projectBackend.project.controller;
//
//import com.projectBackend.project.dto.MusicCommentDTO;
//import com.projectBackend.project.service.MusicCommentService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequestMapping("music_comments")
//@RequiredArgsConstructor
//
//public class MusicCommentController {
//    private final MusicCommentService musicCommentService;
//
//
//
//    //댓글 등록
//
//    @PostMapping("/new")
//    public ResponseEntity<Boolean> registerMusicComment(@RequestBody MusicCommentDTO musicCommentDTO) {
//        boolean result = musicCommentService.registerMusicComment(musicCommentDTO);
//        return ResponseEntity.ok(result);
//    }
//
//    // 댓글 수정
//    @PutMapping("/modify")
//    public ResponseEntity<Boolean> modifyMusicComment(@RequestBody MusicCommentDTO musicCommentDTO) {
//        boolean result = musicCommentService.modifyMusicComment(musicCommentDTO);
//        return ResponseEntity.ok(result);
//    }
//
//    // 댓글 삭제
//    @DeleteMapping("/delete/{musicCommentId}")
//    public ResponseEntity<Boolean> deleteMusicComment(@PathVariable Long musicCommentId) {
//        boolean result = musicCommentService.deleteMusicComment(musicCommentId);
//        return ResponseEntity.ok(result);
//    }
//
//    // 모든 음악 댓글 조회
//    @GetMapping("/list/all")
//    public ResponseEntity<List<MusicCommentDTO>> commentListAll() {
//        // 모든 음악 댓글을 조회.
//        List<MusicCommentDTO> musicComments = musicCommentService.getAllMusicComments();
//
//        // 조회된 댓글이 있는지 확인합니다.
//        if (musicComments != null) {
//            // 조회된 댓글이 있을 경우, OK 상태 코드와 함께 댓글 리스트를 반환.
//            return ResponseEntity.ok(musicComments);
//        } else {
//            // 조회된 댓글이 없을 경우, NOT_FOUND 상태 코드를 반환.
//            return ResponseEntity.notFound().build();
//            //ResponseEntity.notFound()는 HTTP 상태 코드 404(Not Found)를 응답으로 설정하는 메서드.
//            //notFound() 메서드가 반환하는 객체에 build() 메서드를 호출하면 해당 응답에 별도의 본문이 없음을 의미.
//        }
//    }
//
//    // 특정 ID의 음악 댓글 조회
//    @GetMapping("/list/music/{musicId}")
//
//    public ResponseEntity<List<MusicCommentDTO>> getMusicCommentsByMusicId(@PathVariable Long musicId) {
//        //특정 ID의 음악 댓글 조회.
//        List<MusicCommentDTO> musicComments = musicCommentService.getMusicCommentsByMusicId(musicId);
//
//        //조회된 댓글 유무 체크.
//
//        if (musicComments != null) {
//            // 조회된 댓글이 있을 경우, OK 상태 코드와 함께 댓글 리스트를 반환
//            return ResponseEntity.ok(musicComments);
//
//        } else {
//            // 조회된 댓글이 없을 경우, NOT_FOUND 상태 코드를 반환
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//
//
//    // 특정 키워드를 포함한 음악 댓글 검색!
//    @GetMapping("/search")
//    public ResponseEntity<List<MusicCommentDTO>> searchMusicComments(@RequestParam String keyword) {
//        // 특정 키워드를 포함한 음악 댓글 검색.
//        List<MusicCommentDTO> musicComments = musicCommentService.searchMusicComments(keyword);
//
//        //검색된 댓글이 존재하는지 확인.
//        if ( musicComments != null) {
//            //검색된 댓글이 있을 경우, OK 상태 코드와 함께 댓글 리스트를 반환.
//            return ResponseEntity.ok(musicComments);
//        }else {
//            //검색된 댓글이 없을 겨우.
//            return ResponseEntity.notFound() .build();
//        }
//
//    }
//
//}
//
