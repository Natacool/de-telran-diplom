package de.telran.UrlShortener.services;

import de.telran.UrlShortener.dtos.UrlDto;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
//@RequiredArgsConstructor
@NoArgsConstructor
public class UrlService {
    private Map<String, String> mapUrls;

    @PostConstruct
    void init() {
        mapUrls = new HashMap<>();
        mapUrls.put("w", "https://en.wikipedia.org/");
        mapUrls.put("g", "https://google.com/");
        mapUrls.put("y", "https://ya.ru/");
        System.out.println("Выполняем логику при создании объекта "
                + this.getClass().getName());
    }

    public String generateShortUrl(UrlDto longUrl){
        String shortUrl = longUrl.getUrl().substring(8,15);
        return shortUrl;
    }

    public String getLongUrl(String shortUrl){
        String longUrl;
        if (shortUrl.equals("w")){
            longUrl = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BE%D0%BA%D1%80%D0%B0%D1%89%D0%B5%D0%BD%D0%B8%D0%B5_URL";
        } else {
            longUrl = "https://www.google.com/";
        }
        return longUrl;
    }


    @PreDestroy
    void destroy() {
        mapUrls.clear();
        System.out.println("Выполняем логику при окончании работы с  объектом "+this.getClass().getName());
    }
}
