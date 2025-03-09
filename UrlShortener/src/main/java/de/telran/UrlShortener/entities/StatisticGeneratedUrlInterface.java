package de.telran.UrlShortener.entities;

import java.sql.Timestamp;

public interface StatisticGeneratedUrlInterface {
    String getClient();
    Integer getGenerated();
    Timestamp getCreatedAt();
    String getShortUrl();
    String getLongUrl();
}
