package de.telran.UrlShortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UrlCopyEntityDto {
    private Long urlId;
    private String shortUrl;
    private String longUrl;
    private Timestamp createdAt;
    private Timestamp clickedAt;
    private Long clickAmount;
    private Long deleteAfterDays;
    private Long userId;
    private Timestamp updatedAt;
    private Boolean isFavorite;
}
