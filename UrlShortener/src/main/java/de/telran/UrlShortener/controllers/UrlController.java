package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.UrlDto;
import de.telran.UrlShortener.services.UrlService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping
@RequiredArgsConstructor
//@NoArgsConstructor
public class UrlController {
    private final UrlService urlService;


    //@ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(value = "/{url}")
// wrk    public RedirectView redirectUrl(@PathVariable(name = "url") String shortUrl){
    public String redirectUrl(@PathVariable(name = "url") String shortUrl){
//        @GetMapping(value = "/{shortUrl}")
//    public String redirectUrl(@PathVariable String shortUrl){
///    public String redirectUrl(){
///    public RedirectView redirectUrl(@PathVariable String shortLink) {
        //String longUrl = urlService.getLongUrl(shortUrl);
        String longUrl = urlService.getLongUrl(shortUrl);
        return longUrl;
// wrk        RedirectView newView = new RedirectView(longUrl);
// wrk        return newView;
    }

//    @PostMapping(value = "/{longUrl}") //Jackson
    @PostMapping
//    @LogAnnotation
//    public ResponseEntity<UrlDto> generateUrl(@RequestBody @Valid UserDto newUser) { //insert
    public ResponseEntity<String> generateUrl(@RequestBody UrlDto longUrl) { //insert
        String shortUrl = urlService.generateShortUrl(longUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
    }

    // For testing purpose
    @GetMapping(value = "/test")
    public String testGet(){
        return "Привет, я контроллер - UrlsController, " + this.toString();
    }

/*
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    // user ???
    // admin can update timer for deleting
    public UrlDto updateUrl(@RequestBody @Valid CategoryDto updCategory) { //update
        return urlService.updateCategories(updCategory);
    }

    // user can delete own short url
    // admin can delete any short url
    @DeleteMapping(value = "/{id}")
    public void deleteUrl(@PathVariable String id) { //delete
        urlService.deleteUrl(id);
    }
*/


}
