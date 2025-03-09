package de.telran.UrlShortener.entities;

public interface StatisticClickedUrlInterface {
    String getShortUrl();
    Integer getClicked();
    String getClient();
    Long getId();
    String getLongUrl();
}
