package com.projectBackend.project.dto;


import com.projectBackend.project.entity.Music;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicDTO {

    private String musicTitle;
    private String userNickname;
    private String lyricist;
    private String composer;
    private String genre;
    private int purchaseCount;
    private String lyrics;
    private LocalDate releaseDate;
    private String thumbnailImage;
    private String promoImage;
    private String musicInfo;

    // 생성자, 게터, 세터 등은 생략

    public static MusicDTO of(Music music) {
        return MusicDTO.builder()
                .musicTitle(music.getMusicTitle())
                .userNickname(music.getMember().getUserNickname())
                .lyricist(music.getLyricist())
                .composer(music.getComposer())
                .genre(music.getGenre())
                .purchaseCount(music.getPurchaseCount())
                .lyrics(music.getLyrics())
                .releaseDate(music.getReleaseDate())
                .thumbnailImage(music.getThumbnailImage())
                .promoImage(music.getPromoImage())
                .musicInfo(music.getMusicInfo())
                .build();
    }
}
