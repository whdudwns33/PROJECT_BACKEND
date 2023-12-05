package com.projectBackend.project.controller;


import com.projectBackend.project.dto.PerformanceDto;
import com.projectBackend.project.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;
    // 공연 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<PerformanceDto>> performanceList() {
        List<PerformanceDto> list = performanceService.getPerformanceList();
        return ResponseEntity.ok(list);
    }


}
