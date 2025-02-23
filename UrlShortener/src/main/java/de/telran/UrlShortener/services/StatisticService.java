package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.UrlEntity;
import de.telran.UrlShortener.entities.UserEntity;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import de.telran.UrlShortener.mapper.Mappers;
import de.telran.UrlShortener.repositories.UrlRepository;
import de.telran.UrlShortener.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final UrlRepository urlRepository;
    private final UserRepository userRepository;
    private final Mappers mappers;

    public List<StatisticGeneratingUrlResponseDto> getGeneratedUrls(StatisticGeneratingUrlRequestDto requestDto){
        List<StatisticGeneratingUrlResponseDto> generatedUrls = new ArrayList<>();

        String periodDays = "365";
        if (requestDto.getPeriodDays() > 0){
            periodDays = requestDto.getPeriodDays().toString();
        }

        String orderBy = "Urls.CreatedAt";
        if (requestDto.getSortByUser() == true){
            orderBy = "Urls.UserID";
        }

        String descent = "";
        if (requestDto.getDescent() == true){
            descent = "DESC";
        }

        String limitTop = "";
        if (requestDto.getLimitTop() > 0){
            limitTop = " limit " + requestDto.getLimitTop();
        }

        List<UrlEntity> findUrls;
        if (requestDto.getUserId() == null){
            findUrls =  urlRepository.findGeneratedUrlsNative(periodDays, orderBy, descent, limitTop);
        } else {
            findUrls =  urlRepository.findGeneratedUrlsUserNative(requestDto.getUserId(), periodDays, orderBy,descent, limitTop);
        }

        generatedUrls = MapperUtil.convertList(findUrls, mappers::convertToStatisticGeneratingUrlResponseDto);
        return generatedUrls;
    }
    public List<StatisticClickedUrlResponseDto> getClickedUrls(StatisticClickedUrlRequestDto requestDto){
        List<StatisticClickedUrlResponseDto> clickedUrls = new ArrayList<>();

        String periodDays = "365";
        if (requestDto.getPeriodDays() > 0){
            periodDays = requestDto.getPeriodDays().toString();
        }

        String descent = "";
        if (requestDto.getDescent() == true){
            descent = "DESC";
        }

        String limitTop = "";
        if (requestDto.getLimitTop() > 0){
            limitTop = " limit " + requestDto.getLimitTop();
        }

        List<UrlEntity> findUrls;
        if (requestDto.getUserId() == null){
            findUrls =  urlRepository.findClickedUrlsNative(periodDays, descent, limitTop);
        } else {
            findUrls =  urlRepository.findClickedUrlsUserNative(requestDto.getUserId(), periodDays, descent, limitTop);
        }

        clickedUrls = MapperUtil.convertList(findUrls, mappers::convertToStatisticClickedUrlResponseDto);
        return clickedUrls;
    }

    public List<StatisticUserResponseDto> getAllUsers(StatisticUserRequestDto requestUsers){
        List<StatisticUserResponseDto> usersInfo = new ArrayList<>();

        List<String> userRoles = Arrays.asList(
                UserRoleEnum.ADMIN.getTitle(),
                UserRoleEnum.CLIENT.getTitle());
        if (requestUsers.getUserRoles().size() != 0) {
            userRoles = requestUsers
                    .getUserRoles()
                    .stream()
                    .map(s -> s.getTitle())
                    .collect(Collectors.toList());

        }

        List<String> userStatuses = Arrays.asList(
                UserStatusEnum.ACTIVE.getTitle(),
                UserStatusEnum.BLOCKED.getTitle(),
                UserStatusEnum.DELETED.getTitle());
        if (requestUsers.getUserStatuses().size() != 0) {
            userStatuses = requestUsers
                    .getUserStatuses()
                    .stream()
                    .map(s -> s.getTitle())
                    .collect(Collectors.toList());
        }

        String userEmail = "";
        if (requestUsers.getUserEmail() != null || requestUsers.getUserEmail() != ""){
            userEmail = " AND us.Email=(:)";
        }

        List<UserEntity> findUsers = userRepository.findUsersNative(
                userRoles,
                userStatuses,
                userEmail);




        return usersInfo;
    }

    public void getUserInfo(String userEmail) {};
    public void getUserInfo(Long userid) {};

}
