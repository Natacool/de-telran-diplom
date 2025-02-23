package de.telran.UrlShortener.dtos;

import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticUserRequestDto {
    //private List<String> userEmails;
    private String userEmail;
    private List<UserRoleEnum> userRoles;
    private List<UserStatusEnum> userStatuses;
    private Integer periodGenerated;
    private Integer periodClicked;
    private Boolean onlyUserInfo;
}
