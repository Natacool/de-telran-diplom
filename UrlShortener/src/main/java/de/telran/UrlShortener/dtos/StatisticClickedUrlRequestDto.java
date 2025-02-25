package de.telran.UrlShortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticClickedUrlRequestDto {
    private Long userId;
    // if periodDays = 0 - get overall, else clickedAmount for the period
    private Long periodDays;
    private Long limitTop;
    // if descent = true - non-popular first
    private Boolean descent;

}
