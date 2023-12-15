package com.projectBackend.project.dto;
// 곡 등록을 위해서 music 과 user 를 합친 dto

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicUserDto {
    private MusicDTO musicDTO;
    private UserReqDto userReqDto;
}
