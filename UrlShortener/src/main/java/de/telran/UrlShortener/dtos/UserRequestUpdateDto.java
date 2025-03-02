package de.telran.UrlShortener.dtos;

import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestUpdateDto {
    private String email;
    private UserStatusEnum status;
//    private Timestamp lastActiveAt;
}
