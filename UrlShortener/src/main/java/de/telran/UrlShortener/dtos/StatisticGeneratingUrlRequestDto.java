package de.telran.UrlShortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticGeneratingUrlRequestDto {
    private Long userId;
    //private String userEmail;
    private Long periodDays;
    private Boolean sortByUser;
    private Boolean descent;
    private Long limitTop;

}
