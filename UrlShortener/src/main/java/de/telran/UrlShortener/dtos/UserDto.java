package de.telran.UrlShortener.dtos;

import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.sql.Timestamp;

public class UserDto {
//    private Long userId;
    private String email;
//    private UserRoleEnum role;
    private UserStatusEnum status;
//    private Timestamp registeredAt;
//    private Timestamp lastActiveAt;
//    private Timestamp updatedAt;

//    private String passwordHash;
}
