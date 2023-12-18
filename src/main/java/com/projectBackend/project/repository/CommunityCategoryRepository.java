package com.projectBackend.project.repository;

import com.projectBackend.project.entity.CommunityCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityCategoryRepository extends JpaRepository<CommunityCategory, Long> {

}
