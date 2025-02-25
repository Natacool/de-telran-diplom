package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.services.StatisticService;
import de.telran.UrlShortener.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/statistic")
public class StatisticController {
    private final StatisticService statisticService;

    // User APIs
/*
    @GetMapping(value = "/user_generated_urls")
    public ResponseEntity<List<UrlDto>> getUserGeneratedUrls() {
        List<UrlDto> urls = new ArrayList<>();
        //statisticService.getAllUrls();
        return new ResponseEntity<>(urls, HttpStatus.valueOf(200));
    }

    @GetMapping(value = "/user_clicked_urls")
    public ResponseEntity<List<UrlDto>> getUserClickedUrls() {
        List<UrlDto> urls = new ArrayList<>();
        //statisticService.getAllUrls();
        return new ResponseEntity<>(urls, HttpStatus.valueOf(200));
    }
*/

    // Admin APIs
    @GetMapping(value = "/generated_urls")
    public ResponseEntity<List<StatisticGeneratingUrlResponseDto>>
    getAllGeneratedUrls(@RequestBody StatisticGeneratingUrlRequestDto generated) {
        List<StatisticGeneratingUrlResponseDto> urls = statisticService.getGeneratedUrlsStatistic(generated);
        return new ResponseEntity<>(urls, HttpStatus.valueOf(200));
    }

    @GetMapping(value = "/clicked_urls")
    public ResponseEntity<List<StatisticClickedUrlResponseDto>>
    getAllClickedUrls(@RequestBody StatisticClickedUrlRequestDto clicked) {
        List<StatisticClickedUrlResponseDto> urls = statisticService.getClickedUrlsStatistic(clicked);
        return new ResponseEntity<>(urls, HttpStatus.valueOf(200));
    }


    @GetMapping(value = "/users_info")
    public ResponseEntity<List<StatisticUserResponseDto>>
    getAllUsers(@RequestBody StatisticUserRequestDto users) {
        List<StatisticUserResponseDto> resUsers = statisticService.getUsersStatistic(users);
        return new ResponseEntity<>(resUsers, HttpStatus.valueOf(200));
    }
}
