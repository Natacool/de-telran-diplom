package de.telran.UrlShortener.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
