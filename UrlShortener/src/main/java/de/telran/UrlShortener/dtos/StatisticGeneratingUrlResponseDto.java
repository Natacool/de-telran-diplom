package de.telran.UrlShortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticGeneratingUrlResponseDto {
    private String userEmail;
    private Timestamp createdAt;
    private String shortUrl;
    private String longUrl;
}
