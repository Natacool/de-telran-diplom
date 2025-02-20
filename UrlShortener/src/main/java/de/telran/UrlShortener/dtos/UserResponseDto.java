package de.telran.UrlShortener.dtos;

import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto { // todo: make this class as extension of UserOnlyDto
    private Long userId;
    private String email;
    private UserRoleEnum role;
    private UserStatusEnum status;
    private Timestamp registeredAt;
    private Timestamp lastActiveAt; // should be taken as last generated short link
    private Timestamp updatedAt;
    private String passwordHash;
}
