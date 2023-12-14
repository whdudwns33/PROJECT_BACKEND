package com.projectBackend.project.repository;

import com.projectBackend.project.entity.Member;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRepositoryTest {

    @Test()
    public void testFindByUserNickname() {
        // 가짜 Member 객체 생성
        Member fakeMember = new Member();
        fakeMember.setId(1L);
        fakeMember.setUserNickname("testUser");

        // Mocking을 이용한 MemberRepository 객체 생성
        UserRepository memberRepositoryMock = mock(UserRepository.class);

        // 닉네임으로 회원을 찾는 테스트용 닉네임
        String testNickname = "testUser";

        // 닉네임으로 찾는 경우에 가짜 Member 객체를 Optional로 감싸서 반환하도록 설정
        when(memberRepositoryMock.findByUserNickname(testNickname)).thenReturn(Optional.of(fakeMember));

        // findByUserNickname 메서드 호출
        Optional<Member> result = memberRepositoryMock.findByUserNickname(testNickname);

        // 결과 확인
        assertEquals(fakeMember, result.orElse(null), "Expected Member not found");
    }

}