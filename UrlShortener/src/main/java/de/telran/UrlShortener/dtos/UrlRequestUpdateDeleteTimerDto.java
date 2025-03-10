package de.telran.UrlShortener.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlRequestUpdateDeleteTimerDto {
    private String urlId;
    private Long newTimer;
}
