package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.services.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/stat")
public class StatisticController implements StatisticControllerInterface {
    private final StatisticService statisticService;

    @Override
    @GetMapping(value = "/generated_urls")
    public ResponseEntity<List<StatisticGeneratedUrlDto>>
    getGeneratedUrlsStat(@RequestBody StatisticGeneratingUrlRequestDto generated) {
        List<StatisticGeneratedUrlDto> urls = statisticService.getGeneratedUrlsStatistic(generated);
        return new ResponseEntity<>(urls, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/clicked_urls")
    public ResponseEntity<List<StatisticClickedUrlDto>>
    getClickedUrlsStat(@RequestBody StatisticClickedUrlRequestDto clicked) {
        List<StatisticClickedUrlDto> urls = statisticService.getClickedUrlsStatistic(clicked);
        return new ResponseEntity<>(urls, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/users_info")
    public ResponseEntity<List<StatisticUserResponseDto>>
    getUsersStat(@RequestBody StatisticUserRequestDto users) {
        List<StatisticUserResponseDto> resUsers = statisticService.getUsersStatistic(users);
        return new ResponseEntity<>(resUsers, HttpStatus.OK);
    }
}
