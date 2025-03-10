package de.telran.UrlShortener.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description = "DTO to represent user id via email"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String email;
}
