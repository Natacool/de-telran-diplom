package de.telran.UrlShortener.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Schema(
        description = "Statistic: represent DB data for a clicked URL"
)
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
