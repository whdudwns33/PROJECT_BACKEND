package com.projectBackend.project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
    private String musicTitle;
    private String artistName;
    private String lyricist;
    private String composer;
    private String genre;
    private int purchaseCount;
    private int heartCount;
    private String lyrics;
    private Date releaseDate;
    private String thumbnailImage;
    private String promoImage;

}