package com.projectBackend.project.dto;

import com.projectBackend.project.entity.Music;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicDTO {
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

    public static MusicDTO of(Music music) {
        return MusicDTO.builder()
                .musicTitle(music.getMusicTitle())
                .artistName(music.getArtistName())
                .lyricist(music.getLyricist())
                .composer(music.getComposer())
                .genre(music.getGenre())
                .purchaseCount(music.getPurchaseCount())
                .heartCount(music.getHeartCount())
                .lyrics(music.getLyrics())
                .releaseDate(music.getReleaseDate())
                .thumbnailImage(music.getThumbnailImage())
                .promoImage(music.getPromoImage())
                .build();
    }
}
