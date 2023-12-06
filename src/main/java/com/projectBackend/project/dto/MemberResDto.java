package com.projectBackend.project.dto;

import com.projectBackend.project.entity.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResDto {
    private String email;
    private String password;
    private String nickName;
    private String name;
    private String addr;
    private String tel;
    private String gender;
    private int age;
    private int point;
    private String BUSINESS_NUM;

    // Member -> MemberResDto
    public static MemberResDto of(Member member) {
        return MemberResDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
