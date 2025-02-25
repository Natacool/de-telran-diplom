package de.telran.UrlShortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticClickedUrlResponseDto {
    private String shortUrl;
    private Long clickedAmount;
    private Long userId;
    //private String userEmail;
    private String longUrl;
}
