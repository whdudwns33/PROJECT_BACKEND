package com.projectBackend.project.service;


import com.projectBackend.project.dto.PerformanceDto;
import com.projectBackend.project.entity.Performance;
import com.projectBackend.project.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    public List<PerformanceDto> getPerformanceList() {
        List<Performance> performances = performanceRepository.findAll();
        List<PerformanceDto> performanceDtos = new ArrayList<>();
        for (Performance performance : performances) {
            performanceDtos.add(convertEntityToDto(performance));
        }
        return performanceDtos;

    }

    // 퍼포먼스 엔티티를 DTO로 변환
    private PerformanceDto convertEntityToDto(Performance performance) {
        PerformanceDto performanceDto = new PerformanceDto();
        performanceDto.setPerformanceId(performance.getPerformanceId());
        performanceDto.setPerformanceName(performance.getPerformanceName());
        performanceDto.setPerformer(performance.getPerformer());
        performanceDto.setVenue(performance.getVenue());
        performanceDto.setDetailVenue(performance.getDetailVenue());
        performanceDto.setPerformanceDate(performance.getPerformanceDate());
        performanceDto.setPrice(performance.getPrice());
        performanceDto.setDescription(performance.getDescription());
        performanceDto.setSeatCount(performance.getSeatCount());
        performanceDto.setPerformanceImage(performance.getPerformanceImage());
        return performanceDto;
    }
}
