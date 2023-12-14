package com.projectBackend.project.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "community")
@NoArgsConstructor
public class Community {
    @Id
    @Column(name = "community_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long communityId;
    private String title;

    @Lob
    private String content;
    private LocalDateTime regDate;
    @PrePersist
    public void prePersist(){
        regDate = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int viewCount;
    private int voteCount;

    @ElementCollection
    @CollectionTable(name = "media_paths", joinColumns = @JoinColumn(name = "community_id"))
    @Column(name = "path")
    private List<String> mediaPaths;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 전략
    @JoinColumn(name = "category_id")
    private CommunityCategory category; // 카테고리

    @Column(name = "ip_address")
    private String ipAddress;
    private String nickName;
    private String password;
}
