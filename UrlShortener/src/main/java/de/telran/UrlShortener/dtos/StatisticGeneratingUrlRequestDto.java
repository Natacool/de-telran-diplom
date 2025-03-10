package de.telran.UrlShortener.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(
        description = "Statistic: request data for generated URLs information from DB"
)
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
