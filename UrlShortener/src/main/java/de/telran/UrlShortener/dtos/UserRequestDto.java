package de.telran.UrlShortener.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description = "DTO to request user related information"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDto {
    private String email;
    private String password;
}
