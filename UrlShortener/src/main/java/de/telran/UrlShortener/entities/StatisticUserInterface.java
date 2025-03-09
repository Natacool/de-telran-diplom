package de.telran.UrlShortener.entities;

import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;

import java.sql.Timestamp;

public interface StatisticUserInterface {
    Long getUserId();
    String getUserEmail();
    Long getGeneratedAmount();
    Long getClickedAmount();
    UserStatusEnum getStatus();
    UserRoleEnum getRole();
    Timestamp getRegisteredAt();
    Timestamp getLastActiveAt();
    Timestamp getUpdatedAt();
}
