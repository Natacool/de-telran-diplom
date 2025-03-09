package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.*;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import de.telran.UrlShortener.utils.common.CommonUtils;
import de.telran.UrlShortener.utils.mapper.Mappers;
import de.telran.UrlShortener.repositories.UrlRepository;
import de.telran.UrlShortener.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticService {
    private final UrlRepository urlRepository;
    private final UserRepository userRepository;
    private final Mappers mappers;
    private final CommonUtils utils;

    @Async
    public List<StatisticGeneratedUrlDto> getGeneratedUrlsStatistic(
            StatisticGeneratingUrlRequestDto requestDto){
        List<StatisticGeneratedUrlDto> generatedUrls = new ArrayList<>();

        List<String> userEmails = null;
        // && requestDto.getUserEmails().size() != 0){ -> this case used to get anonim
        if (requestDto.getUserEmails() != null) {
            // TODO add check for authorized users - CLIENT can request the only own info
            // admin can request for several users
            userEmails = requestDto.getUserEmails();
        }

        Timestamp periodStartDate = utils.getCurrentTimestampShiftDays(-365);
        if (requestDto.getPeriodDays() != null &&
                requestDto.getPeriodDays() > 0 &&
                requestDto.getPeriodDays() < 500) {
            periodStartDate = utils.getCurrentTimestampShiftDays(-1 * requestDto.getPeriodDays());
        }

        Integer topAmount = 25;
        if (requestDto.getAmountTop() != null &&
                requestDto.getAmountTop() > 0 &&
                requestDto.getAmountTop() < 200) {
            topAmount = requestDto.getAmountTop();
        }

        Boolean needDetails = false;
        if (requestDto.getDetails() != null) {
            needDetails = requestDto.getDetails();
        }

        List<StatisticGeneratedUrlInterface> findUrls;

        if (needDetails) {
            if (userEmails == null){
                findUrls =  urlRepository.findGeneratedUrlsRegisteredDetailsNative(
                        periodStartDate, topAmount);
            } else if(userEmails.size() == 0) {
                findUrls = urlRepository.findGeneratedUrlsIncludeAnonimDetailsNative(
                        periodStartDate, topAmount);
            } else {
                findUrls =  urlRepository.findGeneratedUrlsRegisteredUserDetailsNative(
                        userEmails, periodStartDate, topAmount);
            }
        } else {
            if (userEmails == null){
                findUrls =  urlRepository.findGeneratedUrlsRegisteredNative(
                        periodStartDate, topAmount);
            } else if(userEmails.size() == 0) {
                findUrls = urlRepository.findGeneratedUrlsIncludeAnonimNative(
                        periodStartDate, topAmount);
            } else {
                findUrls =  urlRepository.findGeneratedUrlsRegisteredUserNative(
                        userEmails, periodStartDate, topAmount);
            }
        }

        if (findUrls != null){
            generatedUrls = MapperUtil.convertList(findUrls,
                    mappers::convertToStatisticGeneratedUrlDto);
        }
        return generatedUrls;
    }

    @Async
    public List<StatisticClickedUrlDto> getClickedUrlsStatistic(
            StatisticClickedUrlRequestDto requestDto) {
        Timestamp periodStartDate = utils.getCurrentTimestampShiftDays(-30);
        if (requestDto.getPeriodDays() != null &&
                requestDto.getPeriodDays() > 0 &&
                requestDto.getPeriodDays() < 500
        ){
            periodStartDate = utils.getCurrentTimestampShiftDays(-1 * requestDto.getPeriodDays());
        }

        String orderDirection = "DESC";
        if (requestDto.getNotPopularFirst() != null &&
                requestDto.getNotPopularFirst() == true){
            orderDirection = "ASC";
        }

        Integer topAmount = 100;
        if (requestDto.getAmountTop() != null && requestDto.getAmountTop() > 0){
            topAmount = requestDto.getAmountTop();
        }

        List<String> userEmails = null;
        //&& requestDto.getUserEmails().size() > 0){ -> this case used to get anonim
        if (requestDto.getUserEmails() != null ) {
            // TODO add check for authorized users - CLIENT can request the only own info
            // admin can request for several users
            userEmails = requestDto.getUserEmails();
        }

        List<StatisticClickedUrlInterface> findUrls;
        // TODO add check for authorized users - CLIENT can request the only own info
        // admin can request for several users OR for all including anonim
        if (userEmails == null) {
            findUrls = urlRepository.findClickedUrlsRegisteredNative(
                    periodStartDate, orderDirection, topAmount);
        } else if(userEmails.size() == 0) {
            findUrls = urlRepository.findPopularClickedUrlsIncludeAnonimNative(
                    periodStartDate, orderDirection, topAmount);
        } else {
            findUrls = urlRepository.findClickedUrlsRegisteredUserNative(
                    userEmails, periodStartDate, orderDirection, topAmount);
        }

        List<StatisticClickedUrlDto> clicked = new ArrayList<>();
        if (findUrls != null) {
            clicked = MapperUtil.convertList(findUrls,
                    mappers::convertToStatisticClickedUrlDto);
        }

        return clicked;
    }

    @Async
    public List<StatisticUserResponseDto> getUsersStatistic(StatisticUserRequestDto requestUsers){
        List<StatisticUserResponseDto> usersInfo = new ArrayList<>();

        List<String> userRoles = Arrays.asList(
                UserRoleEnum.ADMIN.getTitle(),
                UserRoleEnum.CLIENT.getTitle());
        if (requestUsers.getUserRoles() != null && requestUsers.getUserRoles().size() != 0) {
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
        if (requestUsers.getUserStatuses() != null && requestUsers.getUserStatuses().size() != 0) {
            userStatuses = requestUsers
                    .getUserStatuses()
                    .stream()
                    .map(s -> s.getTitle())
                    .collect(Collectors.toList());
        }

        List<String> userEmails = null;
        if (requestUsers.getUserEmails() != null && requestUsers.getUserEmails().size() != 0){
            // TODO add check for authorized users - CLIENT can request the only own info
            // admin can request for several users
            userEmails = requestUsers.getUserEmails();
        }

        Integer topAmount = 25;
        if (requestUsers.getAmountTop() != null &&
                requestUsers.getAmountTop() > 0 &&
                requestUsers.getAmountTop() < 200) {
            topAmount = requestUsers.getAmountTop();
        }

        Timestamp periodStartDate = utils.getCurrentTimestampShiftDays(-300);
        if (requestUsers.getPeriodDays() != null &&
                requestUsers.getPeriodDays() > 0 &&
                requestUsers.getPeriodDays() < 500) {
            periodStartDate = utils.getCurrentTimestampShiftDays(-1 * requestUsers.getPeriodDays());
        }

        List<StatisticUserInterface> findUsers;
        if (userEmails == null) {
            findUsers = userRepository.findAllUsersByRoleAndStatusNative(
                    userRoles,
                    userStatuses,
                    periodStartDate,
                    topAmount
            );
        } else {
            findUsers = userRepository.findSpecifiedUsersByRoleAndStatusNative(
                    userEmails,
                    userRoles,
                    userStatuses,
                    periodStartDate,
                    topAmount
            );

        }

        if (findUsers != null) {
            usersInfo = MapperUtil.convertList(findUsers, mappers::convertToStatisticUserResponseDto);
        }
        return usersInfo;
    }
}
