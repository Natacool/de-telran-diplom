package de.telran.UrlShortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticGeneratingUrlRequestDto {
    private Long userId;
    private Long periodDays;
    private Boolean sortByUser;
    private Boolean descent;
    private Long limitTop;

}
