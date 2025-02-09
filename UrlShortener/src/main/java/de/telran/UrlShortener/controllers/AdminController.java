package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.services.UrlService;
import de.telran.UrlShortener.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {
    private final UrlService urlService;
    private final UserService userService;



    // For testing purpose
    @GetMapping(value = "/test")
    public String testGet(){
        return "Привет, я контроллер - AdminController, " + this.toString();
    }
}
