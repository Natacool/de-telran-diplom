package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.services.UrlService;
import liquibase.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping //(value = "/urls")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    // Anonymus, User, Admin  APIs
    @PostMapping(value = "/c")
//    @LogAnnotation
//    public ResponseEntity<UrlDto> generateUrl(@RequestBody @Valid UserDto newUser) { //insert
    public ResponseEntity<String> generateUrl(@RequestBody LongUrlDto longUrl) {
        String shortUrl = urlService.getGeneratedUrl(longUrl);
        // HttpStatus.OK is to avoid HttpStatus.FOUND/HttpStatus.CREATED
        return ResponseEntity.status(HttpStatus.OK).body(shortUrl);
    }

    @GetMapping(value = "/x")
    public RedirectView redirectUrlBody(@RequestBody ShortUrlIdDto shortUrl) {
        String longUrl = urlService.getRedirectUrl(shortUrl.getUrlId());
        if (StringUtil.isEmpty(longUrl)){
           longUrl = "/wrong/url/"+shortUrl.getUrlId();
        }
        RedirectView newView = new RedirectView(longUrl);
        return newView;
    }

    @GetMapping(value = "/{urlId}")
    public RedirectView redirectUrl(@PathVariable String urlId) {
        String longUrl = urlService.getRedirectUrl(urlId);
        if (StringUtil.isEmpty(longUrl)){
            longUrl = "/wrong/url/"+urlId;
        }
        RedirectView newView = new RedirectView(longUrl);
        return newView;
    }

    @GetMapping(value = "/wrong/url/{urlId}")
    public ResponseEntity<String> redirectErrMsg(@PathVariable String urlId){
        String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String errorMsg = "Error: URL: '" + homeURL + "/" + urlId +"' not found.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
    }

    @DeleteMapping //(value = "/r")
    public ResponseEntity<String> deleteByShortUrl(@RequestBody ShortUrlIdDto shortUrl) {
        Boolean ret = urlService.deleteByShortUrl(shortUrl);
        HttpStatus status = HttpStatus.OK;
        String resp = "URL: '" + shortUrl.getUrlId() + "' deleted";
        if (!ret){
            status = HttpStatus.BAD_REQUEST;
            resp = "Error: URL: '" + shortUrl.getUrlId() + "' NOT deleted";
        }
        return ResponseEntity.status(status).body(resp);
    }

    // Admin APIs
    @GetMapping  //(value = "/r")
    public ResponseEntity<List<UrlCopyEntityDto>> getAllUrls() {
        List<UrlCopyEntityDto> users = urlService.getAllUrls();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/long") // /r
    public ResponseEntity<String> getLongUrl(@RequestBody ShortUrlIdDto url){
        String longUrl = urlService.getLongUrl(url.getUrlId());
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (StringUtil.isNotEmpty(longUrl)){
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(longUrl);
    }

    @GetMapping(value = "/short") // /r
    public ResponseEntity<String> getShortUrl(@RequestBody LongUrlDto url){
        String shortUrl = urlService.getShortUrl(url.getUrl());
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (StringUtil.isNotEmpty(shortUrl)){
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(shortUrl);
    }

    @PutMapping //(value = "/r")
    // user ???
    // admin can update timer for deleting
    public ResponseEntity<UrlCopyEntityDto>
    updateUrlDeleteTimer(@RequestBody /*@Valid*/ UrlRequestUpdateDeleteTimerDto updateUrl) {
        UrlCopyEntityDto urlDto = urlService.updateCleanTimer(updateUrl);
        HttpStatus status = HttpStatus.NOT_MODIFIED;
        if (urlDto != null && urlDto.getUrlId() != null && urlDto.getUrlId() > 0){
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(urlDto);
    }
}
