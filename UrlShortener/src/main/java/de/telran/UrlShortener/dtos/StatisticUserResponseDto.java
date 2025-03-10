package de.telran.UrlShortener.dtos;

import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Schema(
        description = "Statistic: represent DB data for a user"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticUserResponseDto {
    private Long userId;
    private String userEmail;
    private Long generatedAmount;
    private Long clickedAmount;
    private UserStatusEnum status;
    private UserRoleEnum role;
    private Timestamp registeredAt;
    private Timestamp lastActiveAt;
    private Timestamp updatedAt;
}
