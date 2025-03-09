package de.telran.UrlShortener.dtos;

import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
