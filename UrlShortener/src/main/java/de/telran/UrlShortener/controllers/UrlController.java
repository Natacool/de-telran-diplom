package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.UrlCopyEntityDto;
import de.telran.UrlShortener.dtos.UrlDto;
import de.telran.UrlShortener.dtos.UrlRequestUpdateDto;
import de.telran.UrlShortener.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping(value = "/urls")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @GetMapping(value = "/x/{url}")
// wrk    public RedirectView redirectUrl(@PathVariable(name = "url") String shortUrl){
    public ResponseEntity<String> redirectUrl(@PathVariable(name = "url") String shortUrl){
        String longUrl = urlService.getRedirectUrl(shortUrl);
        HttpStatus httpStatus = HttpStatus.OK;
        RedirectView newView = new RedirectView(longUrl);
        if (longUrl == ""){
            httpStatus = HttpStatus.NOT_FOUND;

        }

        return ResponseEntity.status(httpStatus).body(longUrl);
// wrk        RedirectView newView = new RedirectView(longUrl);
// wrk        return newView;
    }

    //@GetMapping(value = "/test_short/{url}")
    @GetMapping(value = "/short1")
    public String getLongUrl1(@RequestBody UrlDto url){
        String longUrl = urlService.getLongUrl(url.getUrl());
        return longUrl;
    }

    @GetMapping(value = "/short2")
    public String getLongUrl2(@RequestParam String url){
        String longUrl = urlService.getLongUrl(url);
        return longUrl;
    }

    // work as localhost:8090/urls/long2?url=https://www.google.com
    @GetMapping(value = "/long2")
    public String getShortUrl2(@RequestParam String url){
        String shortUrl = urlService.getShortUrl(url);
        return shortUrl;
    }


    //@GetMapping(value = "/test_long/{url}")
    // work as localhost:8090/urls/long1 + body {"url": "https://www.google.com"}
    @GetMapping(value = "/long1")
    public String getShortUrl1(@RequestBody UrlDto url){
        String shortUrl = urlService.getShortUrl(url.getUrl());
        return shortUrl;
    }

    //    @PostMapping(value = "/{longUrl}") //Jackson
    @PostMapping
//    @LogAnnotation
//    public ResponseEntity<UrlDto> generateUrl(@RequestBody @Valid UserDto newUser) { //insert
    public ResponseEntity<String> generateUrl(@RequestBody UrlDto longUrl) { //insert
        String shortUrl = urlService.getGeneratedUrl(longUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
    }

    // For testing purpose
    @GetMapping(value = "/test")
    public String testGet(){
        return "Привет, я контроллер - UrlsController, " + this.toString();
    }

/////////////////////////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<UrlCopyEntityDto>> getAllUrls() {
        List<UrlCopyEntityDto> users = urlService.getAllUrls();
        return new ResponseEntity<>(users, HttpStatus.valueOf(200));
    }

    @GetMapping(value = "/{id}")
    public String getUrls(@PathVariable Long id){

        String longUrl = new String();//urlService.getLongUrl(shortUrl);
        return longUrl;
// wrk        RedirectView newView = new RedirectView(longUrl);
// wrk        return newView;
    }



    // user can delete own short url
    // admin can delete any short url
    @DeleteMapping(value = "/{link}")
    public void deleteUrl(@PathVariable String link) { //delete
        //urlService.deleteUrl(id);
    }
    @DeleteMapping(value = "/{id}")
    public void deleteUrlById(@PathVariable Long id) { //delete
        urlService.deleteUrlById(id);
    }

    @DeleteMapping(value = "/{url1}")
    public void deleteByShortUrl(@PathVariable String url1) { //delete
        urlService.deleteByShortUrl(url1);
    }

    @DeleteMapping(value = "/{url2}")
    public void deleteByShortUrl(@PathVariable UrlDto url2) { //delete
        urlService.deleteByShortUrl(url2);
    }
    @PutMapping
    // user ???
    // admin can update timer for deleting
    public UrlCopyEntityDto updateUrl(@RequestBody /*@Valid*/ UrlRequestUpdateDto updateUrl) { //update
        return urlService.updateCleanTimer(updateUrl);
    }
}
