package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<String> generateUrl(@RequestBody UrlDto longUrl) {
        String shortUrl = urlService.getGeneratedUrl(longUrl);
        // HttpStatus.OK is to avoid HttpStatus.FOUND/HttpStatus.CREATED
        return ResponseEntity.status(HttpStatus.OK).body(shortUrl);
    }

    @GetMapping(value = "/x")
    public RedirectView redirectUrl(@RequestBody UrlDto shortUrl){
        String longUrl = urlService.getRedirectUrl(shortUrl.getUrl());
        if (longUrl == ""){
            longUrl = "/urls/e";
        }
        RedirectView newView = new RedirectView(longUrl);
        return newView;
    }

    @GetMapping(value = "/e")
    public ResponseEntity<String> redirectErrMsg(){
        String errorMsg = "Error: URL not found.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteByShortUrl(@RequestBody UrlDto shortUrl) {
        Boolean ret = urlService.deleteByShortUrl(shortUrl);
        HttpStatus status = HttpStatus.OK;
        String resp = "URL: '" + shortUrl.getUrl() + "' deleted";
        if (!ret){
            status = HttpStatus.BAD_REQUEST;
            resp = "Error: URL: '" + shortUrl.getUrl() + "' NOT deleted";
        }
        return ResponseEntity.status(status).body(resp);
    }

    // Admin APIs
    @GetMapping
    public ResponseEntity<List<UrlCopyEntityDto>> getAllUrls() {
        List<UrlCopyEntityDto> users = urlService.getAllUrls();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //@GetMapping(value = "/test_short/{url}")
    @GetMapping(value = "/long")
    public ResponseEntity<String> getLongUrl(@RequestBody UrlDto url){
        String longUrl = urlService.getLongUrl(url.getUrl());
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (longUrl != null && longUrl != ""){
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(longUrl);
    }

    @GetMapping(value = "/short")
    public ResponseEntity<String> getShortUrl(@RequestBody UrlDto url){
        String shortUrl = urlService.getShortUrl(url.getUrl());
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (shortUrl != null && shortUrl != ""){
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(shortUrl);
    }

    @PutMapping
    // user ???
    // admin can update timer for deleting
    public ResponseEntity<UrlCopyEntityDto>
    updateUrlDeleteTimer(@RequestBody /*@Valid*/ UrlRequestUpdateDeleteTimerDto updateUrl) {
        UrlCopyEntityDto urlDto = urlService.updateCleanTimer(updateUrl);
        HttpStatus status = HttpStatus.NOT_MODIFIED;
        if (urlDto != null && urlDto.getUserId() != null){
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(urlDto);
    }

    // For testing purpose
    @GetMapping(value = "/test")
    public String testGet(){
        return "Привет, я контроллер - UrlsController, " + this.toString();
    }
}
