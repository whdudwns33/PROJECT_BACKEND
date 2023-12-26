package com.projectBackend.project.controller;

import com.projectBackend.project.dto.TicketerDto;
import com.projectBackend.project.service.TicketerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ticketer")
@RequiredArgsConstructor
public class TicketerController {
    private final TicketerService ticketerService;
    //전체티켓터 조회
//    @GetMapping("/list")
//    public ResponseEntity<List<TicketerDto>> ticketerList() {
//        System.out.println("컨트롤러 ticketerList");
//        List<TicketerDto> list = ticketerService.getTicketerList();
//        System.out.println("컨트롤러 ticketerList : " + list);
//        return ResponseEntity.ok(list);
//    }
    //티켓터 등록
    @PostMapping("/new")
    public ResponseEntity<Boolean> newTicketer(@RequestBody TicketerDto ticketerDto) {
        boolean result = ticketerService.saveTicketer(ticketerDto);
        return ResponseEntity.ok(result);
    }
}
