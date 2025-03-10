package de.telran.UrlShortener.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description = "DTO to represent URL via a short URL and a long URL"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlDto {
    private String urlId;
    private String url;
}
