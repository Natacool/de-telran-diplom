package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.UrlCopyEntityDto;
import de.telran.UrlShortener.dtos.UrlDto;
import de.telran.UrlShortener.dtos.UrlRequestUpdateDto;
import de.telran.UrlShortener.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    // User APIs
    //    @PostMapping(value = "/{longUrl}") //Jackson
    @PostMapping
//    @LogAnnotation
//    public ResponseEntity<UrlDto> generateUrl(@RequestBody @Valid UserDto newUser) { //insert
    public ResponseEntity<String> generateUrl(@RequestBody UrlDto longUrl) { //insert
        String shortUrl = urlService.getGeneratedUrl(longUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
    }

    @GetMapping(value = "/x")
//    public RedirectView redirectUrl(@PathVariable(name = "url") String shortUrl){
    public RedirectView redirectUrl11(@RequestBody UrlDto shortUrl){
        //public ResponseEntity<String> redirectUrl(@PathVariable(name = "url") String shortUrl){
        String longUrl = urlService.getRedirectUrl(shortUrl.getUrl());
        //HttpStatus httpStatus = HttpStatus.OK;
        //RedirectView newView = new RedirectView(longUrl);
        if (longUrl == ""){
            //httpStatus = HttpStatus.NOT_FOUND;
            longUrl = "/urls/error";

        }

        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Location", "/member/uploadImage");
        //return new ResponseEntity<String>(headers,HttpStatus.FOUND);

        //return ResponseEntity.status(httpStatus).body(longUrl);
        RedirectView newView = new RedirectView(longUrl);
        return newView;
    }



    //@ResponseStatus(HttpStatus.NOT_FOUND)
    //@GetMapping(value = "/error/{wrongUrl}")
    @GetMapping(value = "/error")
    //public ResponseEntity<String> redirectUrl1(@PathVariable String wrongUrl){
    public ResponseEntity<String> redirectUrl1(){

        String errorMsg = "Error: URL not found.";
        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Location", "/member/uploadImage");
        //return new ResponseEntity<String>(headers,HttpStatus.FOUND);

//        return ResponseEntity.status(httpStatus).body(longUrl);
// wrk        RedirectView newView = new RedirectView(longUrl);
// wrk        return newView;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }



/////////

//    @DeleteMapping(value = "/{url2}")
@DeleteMapping
//public void deleteByShortUrl(@PathVariable UrlDto url2) { //delete
public void deleteByShortUrl(@RequestBody UrlDto url2) { //delete
    urlService.deleteByShortUrl(url2);
}

    // Admin APIs
    @GetMapping
    public ResponseEntity<List<UrlCopyEntityDto>> getAllUrls() {
        List<UrlCopyEntityDto> users = urlService.getAllUrls();
        return new ResponseEntity<>(users, HttpStatus.valueOf(200));
    }

    //@GetMapping(value = "/test_short/{url}")
    @GetMapping(value = "/get")
    public String getLongUrl1(@RequestBody UrlDto url){
        String longUrl = urlService.getLongUrl(url.getUrl());
        return longUrl;
    }

    @PutMapping
    // user ???
    // admin can update timer for deleting
    public UrlCopyEntityDto updateUrl(@RequestBody /*@Valid*/ UrlRequestUpdateDto updateUrl) { //update
        return urlService.updateCleanTimer(updateUrl);
    }





    // For testing purpose
    @GetMapping(value = "/test")
    public String testGet(){
        return "Привет, я контроллер - UrlsController, " + this.toString();
    }


//    public void createUrl()
//    public String getGeneratedUrl(UrlDto longUrl)
//    public String getRedirectUrl(String shortUrl)
//    public String getLongUrl(String shortUrl)
//    public String getShortUrl(String longUrl)
//    public List<UrlCopyEntityDto> getAllUrls()
//    public UrlCopyEntityDto updateCleanTimer(UrlRequestUpdateDto updateUrl)
//    public boolean deleteUrlById(Long id)
//    public boolean deleteByShortUrl(UrlDto shortUrl)
//    public boolean deleteByShortUrl(String shortUrl)

//HttpHeaders headers = new HttpHeaders();
//headers.add("Location", "/member/uploadImage");
//return new ResponseEntity<String>(headers,HttpStatus.FOUND);



/*

    @GetMapping(value = "/x/{url}")
    public RedirectView redirectUrl(@PathVariable(name = "url") String shortUrl){
        //public ResponseEntity<String> redirectUrl(@PathVariable(name = "url") String shortUrl){
        String longUrl = urlService.getRedirectUrl(shortUrl);
        //HttpStatus httpStatus = HttpStatus.OK;
        //RedirectView newView = new RedirectView(longUrl);
        if (longUrl == ""){
            //httpStatus = HttpStatus.NOT_FOUND;
            longUrl = "/urls/error";

        }

        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Location", "/member/uploadImage");
        //return new ResponseEntity<String>(headers,HttpStatus.FOUND);

        //return ResponseEntity.status(httpStatus).body(longUrl);
        RedirectView newView = new RedirectView(longUrl);
        return newView;
    }

 */






/*
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
*/



/////////////////////////////////////////////////////////




}
