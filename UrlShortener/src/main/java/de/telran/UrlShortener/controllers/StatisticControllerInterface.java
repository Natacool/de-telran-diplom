package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.*;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Statistic", description = "Controller to get statistic data",
        externalDocs = @ExternalDocumentation(
                description = "Link to extended documentation about STATISTIC component",
                url = "https://github.com/Natacool/de-telran-diplom/blob/main/README.md"
        )
)
public interface StatisticControllerInterface {
    //@Hidden
    @Operation(
            summary = "Generated URLs",
            description = "This operation provides parameterizing information about generated urls"
    )
    ResponseEntity<List<StatisticGeneratedUrlDto>>
    getGeneratedUrlsStat(
            @Parameter(description = "Parameters to get Generated URLs statistic"
                    //, required = true,
                    //, example =
            )
            StatisticGeneratingUrlRequestDto generated
    );

    @Operation(
            summary = "Clicked URLs",
            description = "This operation provides parameterizing information about clicked urls"
    )
    ResponseEntity<List<StatisticClickedUrlDto>>
    getClickedUrlsStat(
            @Parameter(description = "Parameters to get Clicked URLs statistic"
                    //, required = true,
                    //, example =
            )
            StatisticClickedUrlRequestDto clicked
    );

    @Operation(
            summary = "Users",
            description = "This operation provides parameterizing information about users"
    )
    ResponseEntity<List<StatisticUserResponseDto>>
    getUsersStat(
            @Parameter(description = "Parameters to get Clicked USERs statistic"
                    //, required = true,
                    //, example =
            )
            StatisticUserRequestDto users
    );
}
