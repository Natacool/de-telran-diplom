package de.telran.UrlShortener.dtos;

import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Schema(
        description = "User: update user's status"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestUpdateDto {
    @Schema(
            description = "User email",
            example = "jack@example.com",
            accessMode = Schema.AccessMode.READ_WRITE
    )
    private String email;

    @Schema(
            description = "User new status",//: \"ACTIVE\", \"BLOCKED\", \"DELETED\"",
            example = "BLOCKED",
            accessMode = Schema.AccessMode.READ_WRITE
    )
    private UserStatusEnum status;
}
