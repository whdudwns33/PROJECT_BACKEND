package com.projectBackend.project.service;


import com.projectBackend.project.dto.PerformanceDto;
import com.projectBackend.project.entity.Member;
import com.projectBackend.project.entity.Performance;
import com.projectBackend.project.entity.Performer;
import com.projectBackend.project.repository.PerformanceRepository;
import com.projectBackend.project.repository.PerformerRepository;
import com.projectBackend.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final PerformerRepository performerRepository;
    private final UserRepository userRepository;

    // 공연 조회
    public List<PerformanceDto> getPerformanceList() {
        List<Performance> performances = performanceRepository.findAll();
        List<PerformanceDto> performanceDtos = new ArrayList<>();
        System.out.println("서비스 getPerformanceList");
        for (Performance performance : performances) {
            performanceDtos.add(convertEntityToDto(performance));
        }
        return performanceDtos;

    }

    // 공연 등록
    public boolean savePerformance(PerformanceDto performanceDto) {
        boolean isTrue = false;
        try {
            Performance performance = new Performance();
            performance.setPerformanceName(performanceDto.getPerformanceName());
            performance.setVenue(performanceDto.getVenue());
            performance.setDetailVenue(performanceDto.getDetailVenue());
            performance.setPerformanceDate(performanceDto.getPerformanceDate());
            performance.setPrice(performanceDto.getPrice());
            performance.setDescription(performanceDto.getDescription());
            performance.setSeatCount(performanceDto.getSeatCount());
            performance.setPerformanceImage(performanceDto.getPerformanceImage());
            performance = performanceRepository.save(performance);

            for (String performerName : performanceDto.getPerformer()) {
                Member member = userRepository.findByUserNickname(performerName)
                        .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
                Performer performer = new Performer();
                performer.setPerformance(performance);
                performer.setMember(member);
                performerRepository.save(performer);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
//        try {
//            Performance performance = new Performance();
////             멤버 검증 필요
//            Member member = userRepository.findByUserNickname(performanceDto.getPerformer().toString()).orElseThrow(
//                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
//            );
//            performance.setPerformanceName(performanceDto.getPerformanceName());
//            performance.setVenue(performanceDto.getVenue());
//            performance.setDetailVenue(performanceDto.getDetailVenue());
//            performance.setPerformanceDate(performanceDto.getPerformanceDate());
//            performance.setPrice(performanceDto.getPrice());
//            performance.setDescription(performanceDto.getDescription());
//            performance.setSeatCount(performanceDto.getSeatCount());
//            performance.setPerformanceImage(performanceDto.getPerformanceImage());
//            performanceRepository.save(performance);
//            isTrue = true;
//            if (isTrue) {
//                Performer performer = new Performer();
//                performer.setPerformance(performance);
//                performer.setMember(member);
//                performerRepository.save(performer);
//            } else {
//                return false;
//            }
//
//
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    // 공연 삭제
    public void deleteAll() {
        performanceRepository.deleteAll();
    }

    // 페이지네이션
    public List<PerformanceDto> getPerformanceList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Performance> performances = performanceRepository.findAll(pageable).getContent();
        List<PerformanceDto> performanceDtos = new ArrayList<>();
        for (Performance performance : performances) {
            performanceDtos.add(convertEntityToDto(performance));
        }
        return performanceDtos;
    }

    // 페이지 수 조회
    public int getPerformancePage(Pageable pageable) {
        return performanceRepository.findAll(pageable).getTotalPages();
    }

    // 퍼포먼스 엔티티를 DTO로 변환
    private PerformanceDto convertEntityToDto(Performance performance) {
        PerformanceDto performanceDto = new PerformanceDto();
        performanceDto.setPerformanceId(performance.getPerformanceId());
        performanceDto.setPerformanceName(performance.getPerformanceName());
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
