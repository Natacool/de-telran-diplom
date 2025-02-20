package de.telran.UrlShortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticGeneratingUrlResponseDto {
    private Integer periodDays;
    private Long total;
    private Map<Long, Long> userInfo;
    //private Map<String, Long> userInfo;

    //private Map<Long, Map<Timestamp, Long>> userInfo;
    //private Map<String, Map<Timestamp, Long>> userInfo;

}
