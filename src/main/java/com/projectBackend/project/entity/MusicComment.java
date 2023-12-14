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
    @Column(name = "musiccomment_id")
    private String musicCommentID;

    @Column(name = "comment_content")
    private String commentContent;

    @Column(name = "comment_Date")
    private LocalDateTime commentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private Member member;


}
