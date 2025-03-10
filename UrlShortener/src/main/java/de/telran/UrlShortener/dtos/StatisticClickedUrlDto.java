package de.telran.UrlShortener.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Statistic: clicked URLs information from DB")
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
