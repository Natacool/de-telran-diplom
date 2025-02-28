package de.telran.UrlShortener.configure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@Getter
@Service
@RequiredArgsConstructor
public class UrlGenerationConfig {
    private final String allowedSymbols = "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "0123456789";
    private final int shortUrlLength = 7;
    private final long base = 62L;
    private final boolean nanoTime = false;
}
