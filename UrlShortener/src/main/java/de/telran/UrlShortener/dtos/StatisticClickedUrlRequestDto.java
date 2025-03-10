package de.telran.UrlShortener.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(
        description = "Statistic: request data for clicked URLs information from DB"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticClickedUrlRequestDto {
    @Schema(
            description = "User emails: can be empty OR filled list with emails OR null",
            example = "[\"jack@example.com\", \"alice@example.com\", \"bob@example.com\"]",
            accessMode = Schema.AccessMode.READ_WRITE
    )
    private List<String> userEmails;
    // if periodDays = 0 - get overall, else clickedAmount for the period

    @Schema(
            description = "Period in previous days for statistic information",
            example = "14",
            accessMode = Schema.AccessMode.READ_WRITE
    )
    private Integer periodDays;

    private Integer amountTop;
    // if descent = true - non-popular first
    private Boolean notPopularFirst;

}
