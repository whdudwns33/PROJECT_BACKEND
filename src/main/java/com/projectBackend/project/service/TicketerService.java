package com.projectBackend.project.service;

import com.projectBackend.project.dto.TicketerDto;
import com.projectBackend.project.entity.Ticketer;
import com.projectBackend.project.repository.TicketerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketerService {
    private final TicketerRepository ticketerRepository;

    // 공연자 조회
    public List<TicketerDto> getTicketerList() {
        List<Ticketer> ticketers = ticketerRepository.findAll();
        List<TicketerDto> ticketerDtos = new ArrayList<>();
        System.out.println("서비스 getTicketerList");
        for (Ticketer ticketer : ticketers) {
            ticketerDtos.add(convertEntityToDto(ticketer));
        }
        return ticketerDtos;

    }

    // 공연자 등록
//    public boolean saveTicketer(TicketerDto ticketerDto) {
//        try {
//            Ticketer ticketer = new Ticketer();
//        }
//        return true;
//    }


    private TicketerDto convertEntityToDto(Ticketer ticketer) {
        TicketerDto ticketerDto = new TicketerDto();
        ticketerDto.setTicketerId(ticketer.getTicketerId());
        ticketerDto.setPerformanceId(ticketer.getPerformance().getPerformanceId().toString());
        ticketerDto.setTicketer(ticketer.getMember().getUserNickname());
        return ticketerDto;
    }
}
