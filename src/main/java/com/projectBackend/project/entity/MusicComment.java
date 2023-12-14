package com.projectBackend.project.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "music_comment")
public class MusicComment {

    @Id
    @Column(name = "musiccomment_id", length = 20)
    private String musicCommentID;

    @Column(name = "comment_content", length = 500, nullable = false)
    private String commentContent;

    @Column(name = "comment_Date", nullable = false)
    private LocalDateTime commentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", nullable = false)
    private Member member;


}
