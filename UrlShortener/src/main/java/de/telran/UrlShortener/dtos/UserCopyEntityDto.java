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
public class UserCopyEntityDto {
    private Long userId;
    private String email;
    private UserRoleEnum role;
    private UserStatusEnum status;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private String passwordHash;
}
