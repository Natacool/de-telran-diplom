package de.telran.UrlShortener.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Schema(
        description = "Statistic: represent DB data for a generated URL"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticGeneratingUrlResponseDto {
    private String userEmail;
    private Timestamp createdAt;
    private String shortUrl;
    private String longUrl;
}
