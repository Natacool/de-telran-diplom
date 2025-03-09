package de.telran.UrlShortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticClickedUrlDto
{
    private String shortUrl;
    private Integer clicked;
    private String client;
    private String longUrl;
}
