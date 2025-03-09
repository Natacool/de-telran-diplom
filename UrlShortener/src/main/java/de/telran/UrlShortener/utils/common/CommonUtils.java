package de.telran.UrlShortener.utils.common;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@NoArgsConstructor
public class CommonUtils {
    public String getHomeUrl() {
        String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        return homeURL;
    }

    public Timestamp getCurrentTimestamp() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        return now;
    }

    public Timestamp getCurrentTimestampShiftDays(Integer days) {
        Timestamp now;
        if (days == null || days == 0) {
            now = Timestamp.valueOf(LocalDateTime.now());
        } else {
            now = Timestamp.valueOf(LocalDateTime.now().plusDays(days));
        }
        return now;
    }

}
