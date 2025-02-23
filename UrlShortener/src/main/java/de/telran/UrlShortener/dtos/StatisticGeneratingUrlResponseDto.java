package de.telran.UrlShortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticGeneratingUrlResponseDto {
    private Timestamp createdAt;
    private Long userId;
    private String shortUrl;
    private String longUrl;
}
