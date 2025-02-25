package de.telran.UrlShortener.dtos;

import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestUpdateStatusDto {
    private String email;
    private UserStatusEnum status;
}
