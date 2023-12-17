package com.projectBackend.project.dto;
// 곡 등록을 위해서 music 과 user 를 합친 dto

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicUserDto {
    private MusicDTO musicDTO;
    // 프론트에서 백으로 요청
    private UserReqDto userReqDto;
    // 백에서 프론트로 요청
    private UserResDto userResDto;
}
