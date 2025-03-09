package de.telran.UrlShortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticClickedUrlRequestDto {
    private List<String> userEmails;
    // if periodDays = 0 - get overall, else clickedAmount for the period
    private Integer periodDays;
    private Integer amountTop;
    // if descent = true - non-popular first
    private Boolean notPopularFirst;

}
