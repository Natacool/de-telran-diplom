package de.telran.UrlShortener.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Parameter, which is an original URL, to get corresponding short URL from DB as a string")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LongUrlDto {
    private String url;
}
