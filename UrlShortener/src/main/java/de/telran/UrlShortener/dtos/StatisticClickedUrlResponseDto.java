package de.telran.UrlShortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticClickedUrlResponseDto {
    private Long clickedAmount;
    private String shortUrl;
    private Timestamp clickedAt;
    private String userEmail;
    private String longUrl;
}
