package de.telran.UrlShortener.dtos;

import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(
        description = "Statistic: request data for users information from DB"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticUserRequestDto {
    private List<String> userEmails;
    private List<UserRoleEnum> userRoles;
    private List<UserStatusEnum> userStatuses;
    private Integer periodDays;
    private Integer amountTop;
}
