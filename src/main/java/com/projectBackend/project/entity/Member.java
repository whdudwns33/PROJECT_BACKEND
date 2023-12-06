package com.projectBackend.project.entity;

import com.projectBackend.project.constant.Authority;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "member")

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Builder
    public Member(String email, String password, String nickName, String name, String addr, String tel, String gender, String BUSINESS_NUM, int point, int age, Authority authority) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.addr = addr;
        this.tel = tel;
        this.BUSINESS_NUM = BUSINESS_NUM;
        this.name = name;
        this.gender = gender;
        this.point = point;
        this.authority = authority;
        this.age = age;
    }
}
