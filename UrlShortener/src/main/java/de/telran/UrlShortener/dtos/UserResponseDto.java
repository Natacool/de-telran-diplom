package de.telran.UrlShortener.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Schema(
        description = "DTO to represent a user related information"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto { // todo: make this class as extension of UserOnlyDto
    private Long userId;
    private String email;
    private UserRoleEnum role;
    private UserStatusEnum status;
    private Timestamp registeredAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp lastActiveAt; // ? should be taken as last generated short link

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String passwordHash;
}
