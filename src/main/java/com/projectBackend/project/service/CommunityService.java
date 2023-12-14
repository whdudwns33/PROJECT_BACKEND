package com.projectBackend.project.service;

import com.projectBackend.project.dto.CommunityDTO;
import com.projectBackend.project.entity.*;
import com.projectBackend.project.repository.CommunityCategoryRepository;
import com.projectBackend.project.repository.CommunityRepository;
import com.projectBackend.project.repository.CommunityViewRepository;
import com.projectBackend.project.repository.CommunityVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;
    private final CommunityCategoryRepository categoryRepository;
    private final CommunityViewRepository viewRepository;
    private final CommunityVoteRepository communityVoteRepository;
    public boolean saveCommunity(CommunityDTO communityDTO, HttpServletRequest request) {
        try{
            Community community = new Community();

            if (communityDTO.getEmail() != null && !communityDTO.getEmail().isEmpty()) {
                Member member = memberRepository.findByEmail(communityDTO.getEmail()).orElseThrow(
                        () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
                );
                community.setMember(member);
            } else {
                String clientIp = request.getHeader("X-Forwarded-For");

                if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
                    clientIp = request.getRemoteAddr();
                }
                community.setIpAddress(clientIp);
                community.setNickName(communityDTO.getNickName());
                community.setPassword(communityDTO.getPassword());
            }
            CommunityCategory category = categoryRepository.findById(communityDTO.getCategoryId()).orElseThrow(
                    () -> new RuntimeException("해당 카테고리가 존재하지 않습니다.")
            );

            community.setTitle(communityDTO.getTitle());
            community.setCategory(category);
            community.setContent(communityDTO.getContent());
            community.setMediaPaths(communityDTO.getMedias());
            communityRepository.save(community);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public List<CommunityDTO> getCommunityList(){
        List<Community> communities = communityRepository.findAll();
        List<CommunityDTO> communityDTOS = new ArrayList<>();
        for (Community community : communities){
            communityDTOS.add(convertEntityToDTO(community));
        }
        return communityDTOS;
    }
    public CommunityDTO getCommunityDetail(Long id , HttpServletRequest request) {
        Community community = communityRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 게시물이 존재하지 않습니다.")
        );
        String visitorIp = request.getHeader("X-Forwarded-For");
        if (visitorIp == null || visitorIp.isEmpty() || "unknown".equalsIgnoreCase(visitorIp)) {
            visitorIp = request.getRemoteAddr();
        }
        final String finalVisitorIp = visitorIp;
        List<CommunityView> communityViews = viewRepository.findByCommunity(community);
        if (communityViews.stream().noneMatch(view -> view.getIp().equals(finalVisitorIp))) {
            community.setViewCount(community.getViewCount() + 1);
            CommunityView communityView = new CommunityView();
            communityView.setCommunity(community);
            communityView.setIp(finalVisitorIp);
            viewRepository.save(communityView);
        }

        communityRepository.save(community);
        return convertEntityToDTO(community);
    }
    public boolean modifyCommunity(Long id, CommunityDTO communityDTO){
        try {
            Community community = communityRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
            );
            community.setTitle(communityDTO.getTitle());
            community.setContent(communityDTO.getContent());
            community.setMediaPaths(communityDTO.getMedias());
            communityRepository.save(community);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 게시글 삭제
    public boolean deleteCommunity(Long id) {
        try {
            communityRepository.deleteById(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // 게시글 검색
    public List<CommunityDTO> searchCommunity(String keyword){
        List<Community> communities = communityRepository.findByTitleContaining(keyword);
        List<CommunityDTO> communityDTOS = new ArrayList<>();
        for (Community community : communities) {
            communityDTOS.add(convertEntityToDTO(community));
        }
        return communityDTOS;
    }
    // 게시글 페이징
    public List<CommunityDTO> getCommunityList(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<Community> communities= communityRepository.findAll(pageable).getContent();
        List<CommunityDTO> communityDTOS = new ArrayList<>();
        for (Community community : communities) {
            communityDTOS.add(convertEntityToDTO(community));
        }
        return communityDTOS;
    }
    // 카테고리별 게시글 페이징
    public List<CommunityDTO> getCommunityListByCategory(Long categoryId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<Community> communities= communityRepository.findByCategory_CategoryId(categoryId, pageable).getContent();
        List<CommunityDTO> communityDTOS = new ArrayList<>();
        for (Community community : communities) {
            communityDTOS.add(convertEntityToDTO(community));
        }
        return communityDTOS;
    }
    // 페이지 수 조회
    public int getCommunity(Pageable pageable){
        return communityRepository.findAll(pageable).getTotalPages();
    }
    // 카테고리에 따른 페이지 수 조회
    public int getCommunityTotalPagesByCategory(Long categoryId, Pageable pageable) {
        return communityRepository.findByCategory_CategoryId(categoryId, pageable).getTotalPages();
    }

    // 개념글 추천
    public void vote(Long communityId, String userEmail, String visitorIp, boolean isUpvote) {
        Optional<Community> communityOptional = communityRepository.findById(communityId);
        if (!communityOptional.isPresent()) {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }

        Community community = communityOptional.get();

        // 로그인한 경우 이메일로 비로그인일 경우 IP로 중복 추천 체크
        Optional<CommunityVote> voteOptional;
        if (userEmail != null && !userEmail.isEmpty()) {
            voteOptional = communityVoteRepository.findByCommunityAndUserEmail(community, userEmail);
        } else {
            voteOptional = communityVoteRepository.findByCommunityAndIp(community, visitorIp);
        }

        // 이미 추천했는지 확인
        if (voteOptional.isPresent()) {
            throw new IllegalArgumentException("이미 추천하셨습니다.");
        }

        // 추천 수 증가 또는 감소
        if (isUpvote) {
            community.setVoteCount(community.getVoteCount() + 1);
        } else {
            community.setVoteCount(community.getVoteCount() - 1);
        }
        communityRepository.save(community);

        // 추천 기록 저장
        CommunityVote vote = new CommunityVote();
        vote.setCommunity(community);
        vote.setIp(visitorIp);
        vote.setUpvote(isUpvote);
        communityVoteRepository.save(vote);
    }
    // 게시글 엔티티를 DTO로 변환
    private CommunityDTO convertEntityToDTO(Community community) {
        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.setId(community.getCommunityId());
        communityDTO.setTitle(community.getTitle());
        communityDTO.setContent(community.getContent());
        communityDTO.setIpAddress(community.getIpAddress());
        communityDTO.setMedias(community.getMediaPaths());
        communityDTO.setNickName(community.getNickName());
        communityDTO.setPassword(community.getPassword());
        communityDTO.setViewCount(community.getViewCount());
        communityDTO.setVoteCount(community.getVoteCount());
        communityDTO.setCategoryId(community.getCategory().getCategoryId());

        if (community.getMember() != null) {
            communityDTO.setEmail(community.getMember().getEmail());
        }
        communityDTO.setRegDate(community.getRegDate());
        return communityDTO;
    }
}
