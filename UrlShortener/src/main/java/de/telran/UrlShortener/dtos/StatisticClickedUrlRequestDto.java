package de.telran.UrlShortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticClickedUrlRequestDto {
    // if userIds = null - get for all users, else for users from the list
    private List<Long> userIds;
    // if periodDays = 0 - get overall, else clickedAmount for the period
    private Integer periodDays;
    private Integer limitTop;
    // if descent = true - non-popular first
    private Boolean descent;

    // if groupUser = true - sort per User, then per amount, else per amount only
    private Boolean groupUser;
    //private List<String> userEmails;
    //private boolean total;
}
