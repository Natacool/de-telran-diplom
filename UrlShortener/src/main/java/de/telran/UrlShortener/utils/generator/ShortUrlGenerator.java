package de.telran.UrlShortener.utils.generator;

import de.telran.UrlShortener.configure.UrlGenerationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ShortUrlGenerator {
    private final UrlGenerationConfig cfg;

    public String generateShortUrl() {
        int keyLength = cfg.getShortUrlLength();
        String allowedCharacters = cfg.getAllowedSymbols();
        StringBuilder keyBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < keyLength; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            keyBuilder.append(allowedCharacters.charAt(randomIndex));
        }
        return keyBuilder.toString();
    }

    public String generateShortUrl2() {
        boolean nano = cfg.isNanoTime();
        int keyLength;// = cfg.getShortUrlLength();
        Long base = cfg.getBase();
        // for milliseconds - length = 7
        // for nanoseconds - length = 9
        Long timeNumber = 0L;
        if (nano == true){
            keyLength = 9;
            timeNumber = System.nanoTime();
        } else {
            keyLength = 7;
            timeNumber = System.currentTimeMillis();
        }

        List<Long> listRest = new ArrayList<>();
        String allowedCharacters = cfg.getAllowedSymbols();
        StringBuilder keyBuilder = new StringBuilder();
        int rest = 0;

        while (timeNumber > base){
            rest = (int)(timeNumber % base);
            keyBuilder.append(allowedCharacters.charAt(rest));

            timeNumber = timeNumber / base;
            if (timeNumber < base) {
                rest = Math.toIntExact(timeNumber);
                keyBuilder.append(allowedCharacters.charAt(rest));
            }
        }

        return keyBuilder.toString();
    }

    private List<Long> convertBase62(Long number, Long base){
        List<Long> listRest = new ArrayList<>();
        Long div = number;
        Long rest = 0L;
        while (div > base){
            rest = div % base;
            div = div / base;
            listRest.add(rest);
            if (div < base) {
                listRest.add(div);
            }
        }
        return listRest;
    }

    private boolean CheckConversion(Long base, List<Long> listRest, Long expected){
        Long result = 0L;
        for (int i=0; i<listRest.size(); i++) {
            result = result + listRest.get(i) * (long)(Math.pow(base, i));
        }
        return result == expected;
    }
}
