package com.projectBackend.project.entity;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "music")
@Getter  @Setter @ToString
@NoArgsConstructor

public class Music {
    @Id
    @Column(name = "music_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long musicId;

    @Column(name = "music_title", length = 50, nullable = false)
    private String musicTitle;

    @Column(name = "lyricist", length = 30, nullable = false)
    private String lyricist;

    @Column(name = "composer", length = 30, nullable = false)
    private String composer;

    @Column(name = "genre", length = 20, nullable = false)
    private String genre;

    @Column(name = "purchase_count", nullable = false)
    private int purchaseCount;

    @Column(name = "lyrics", length = 2000, nullable = false)
    private String lyrics;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "thumbnail_img", length = 100, nullable = false)
    private String thumbnailImage;

    @Column(name = "promo_img", length = 100, nullable = false)
    private String promoImage;

    @Column(name = "music_info", length = 100, nullable = false)
    private String musicInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_nickname", nullable = false) // 외래 키 지정
    private Member member;
}