package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.LongUrlDto;
import de.telran.UrlShortener.dtos.ShortUrlIdDto;
import de.telran.UrlShortener.dtos.UrlCopyEntityDto;
import de.telran.UrlShortener.dtos.UrlRequestUpdateDeleteTimerDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Tag(name = "URLs", description = "Controller to generate a short url or redirect to original url",
        externalDocs = @ExternalDocumentation(
                description = "Link to extended documentation about URL component",
                url = "https://github.com/Natacool/de-telran-diplom/blob/main/README.md"
        )
)
public interface UrlControllerInterface {

    @Operation(
            summary = "Generate URL",
            description = "This operation generates a short URL for provided long URL"
    )
    ResponseEntity<String> generateUrl(
            @Parameter(description = "Parameter to get Generated URLs statistic"
                    , required = true
                    , example = "https://en.wikipedia.org/wiki/Free_and_open-source_software"
            )
            LongUrlDto longUrl
    );

    @Operation(
            summary = "Redirect to original URL",
            description = "This operation redirects to original URL by provided short URL"
    )
    RedirectView redirectUrlBody(
            @Parameter(description = "Parameter for redirection to original URL"
                    , required = true
                    , example = "Qsq2We3"
            )
            ShortUrlIdDto shortUrl
    );

    @Operation(
            summary = "Redirect to original URL via url string ",
            description = "This operation redirects to original url if exists (can be used in browser)"
    )
    public RedirectView redirectUrl(
            @Parameter(description = "Short URL ID from DB"
                    , required = true
                    , example = "Qsq2We3"
            )
            String urlId
    );

    @Operation(
            summary = "Show error message",
            description = "This operation shows error in case of wring short URL)"
    )
    ResponseEntity<String> redirectErrMsg(
            @Parameter(description = "Short URL, which does NOT exist in DB"
                    , example = "Qsq2We3"
            )
            String urlId
    );

    @Operation(
            summary = "Delete short URL",
            description = "This operation deletes DB entry by a short URL)"
    )
    ResponseEntity<String> deleteByShortUrl(
            @Parameter(description = "Short URL, which should be deleted from DB"
            )
            ShortUrlIdDto shortUrl
    );

    @Operation(
            summary = "Get all DB entries from DB URLs table",
            description = "This operation gets all DB URLs entries with all columns"
    )
    ResponseEntity<List<UrlCopyEntityDto>> getAllUrls();

    @Operation(
            summary = "Get original URL",
            description = "This operation gets original URL from DB if exists by short URL; without redirecting"
    )
    ResponseEntity<String> getLongUrl(
            @Parameter(description = "Parameter to get original URL, if exists"
                    , required = true
                    , example = "Qsq2We3"
            )
            ShortUrlIdDto url
    );

    @Operation(
            summary = "Get short URL",
            description = "This operation gets short URL from DB if exists by long URL; without generating"
    )
    ResponseEntity<String> getShortUrl(
            @Parameter(description = "Parameter to get short URL, if exists"
                    , required = true
                    , example = "https://en.wikipedia.org/wiki/Free_and_open-source_software"
            )
            LongUrlDto url
    );

    @Operation(
            summary = "Update clean up timer",
            description = "This operation updates clean up timer for a short URL (in days)"
    )
    ResponseEntity<UrlCopyEntityDto>
    updateUrlDeleteTimer(
            @Parameter(description = "Parameter to update days until deleting for a short URL, if exists"
                    , required = true
            )
            UrlRequestUpdateDeleteTimerDto updateUrl
    );
}
