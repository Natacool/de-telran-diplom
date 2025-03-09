package de.telran.UrlShortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticGeneratingUrlRequestDto {
    // first 3 params - to get info like: user, amount_created (sorted by amount)
    private List<String> userEmails;
    private Integer periodDays;
    private Integer amountTop;
    // with parameter - to get info like: user, created_at, shortUrl, LongUrl (sorted by user)
    private Boolean details; //
}
