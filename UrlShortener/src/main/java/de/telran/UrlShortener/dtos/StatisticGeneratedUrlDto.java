package de.telran.UrlShortener.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Schema(description = "Statistic: generated URLs information from DB")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticGeneratedUrlDto {
    private String client;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer generated;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String shortUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String longUrl;
}
