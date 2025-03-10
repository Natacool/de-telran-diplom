package de.telran.UrlShortener.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Schema(
        description = "DTO equals URL entity from DB"
)
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
