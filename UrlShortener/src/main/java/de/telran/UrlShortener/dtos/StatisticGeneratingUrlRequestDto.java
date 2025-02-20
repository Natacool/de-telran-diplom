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
    private List<Long> userIds;
    //private List<String> userEmails;
    //private Timestamp startDate;
    //private Timestamp endDate;
    private Integer periodDays;
    //private boolean total;

    //private Integer limitUser;
    private Boolean descent;

}
