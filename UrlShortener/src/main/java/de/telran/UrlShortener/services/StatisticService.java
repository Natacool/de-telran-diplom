package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.UrlEntity;
import de.telran.UrlShortener.entities.UserEntity;
import de.telran.UrlShortener.mapper.Mappers;
import de.telran.UrlShortener.repositories.UrlRepository;
import de.telran.UrlShortener.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@NoArgsConstructor
public class StatisticService {
    private final UrlRepository urlRepository;
    private final UserRepository userRepository;
    private final Mappers mappers;




    public void getGeneratedUrls(){
        //StatisticGeneratingUrlRequestDto in = new StatisticGeneratingUrlRequestDto();
        // UrlEntity = mapper.convert(StatisticGeneratingUrlRequestDto)
        // UrlGeneratedQuery = qq(UrlEntity)
        // generatedUrlList = UrlRepo.findGeneratedUrls(UrlGeneratedQuery)
        // return generatedUrlList
    }
    public void getClickedUrls(){
        //StatisticClickedUrlRequestDto in = new StatisticClickedUrlRequestDto();
        // UrlEntity = mapper.convert(StatisticClickedUrlRequestDto)
        // UrlClickedQuery = qq(UrlEntity)
        // clickedUrlList = UrlRepo.findClickedUrls(UrlClickedQuery)
        // return clickedUrlList
    }

    public void getUsersInfo(){
        // StatisticUserRequestDto in = new StatisticUserRequestDto();
        // UserEntity = mapper.convert(StatisticGeneratingUrlRequestDto)
        // UserQuery = qq(UserEntity)
        // userList = UserRepo.findUsers(UserQuery)
        // return userList

    }

    //////////////////////////////////////////
    //GetGeneratedUrls(USER, period) – user, admin [statistic] – email, period, generated_amount, (?sort), (?limit)


    //////////////////////////////////////////
    //GetClickedUrls(USER, ASC/DESC, period) – user, admin [statistic] - short, clicked_amount, sort, days, limit, long
    // Role = ADMIN
    // input: String email / Long userId
    // output: UserAdminResponseDto (
    // userId, email, role, status, registeredAt, activeAt,
    // amountOfCreated list(created per day, week, month)
    // amountOfClicked list(clicked per day, week, month) )


    //////////////////////////////////////////
    //GetAllUrls(USER) – user, admin [statistic] – short, clicked_amount, long
    // Role = ADMIN
    // input: String email / Long userId
    // output: UserAdminResponseDto (
    // userId, email, role, status, registeredAt, activeAt,
    // amountOfCreated list(created per day, week, month)
    // amountOfClicked list(clicked per day, week, month) )


    //////////////////////////////////////////
    //GetAllUrls() – admin [statistic] – short, clicked_amount, long
    // Role = ADMIN
    // input: String email / Long userId
    // output: UserAdminResponseDto (
    // userId, email, role, status, registeredAt, activeAt,
    // amountOfCreated list(created per day, week, month)
    // amountOfClicked list(clicked per day, week, month) )


    //////////////////////////////////////////
    //GetUrls(USER, mostly clicked/not clicked, ASC/DESC, limit) – user, admin [statistic]
    // Role = ADMIN
    // input: String email / Long userId
    // output: UserAdminResponseDto (
    // userId, email, role, status, registeredAt, activeAt,
    // amountOfCreated list(created per day, week, month)
    // amountOfClicked list(clicked per day, week, month) )


    //////////////////////////////////////////
    //GetAllUsers() – admin [statistic] – email, status, role, generated_amount
    // Role = ADMIN
    // input: String email / Long userId
    // output: UserAdminResponseDto (
    // userId, email, role, status, registeredAt, activeAt,
    // amountOfCreated list(created per day, week, month)
    // amountOfClicked list(clicked per day, week, month) )



    //////////////////////////////////////////
    //GetUsers(active/not_active, role, status, ASC/DESC, limit) – admin [statistic]
    // Role = ADMIN
    // input: String email / Long userId
    // output: UserAdminResponseDto (
    // userId, email, role, status, registeredAt, activeAt,
    // amountOfCreated list(created per day, week, month)
    // amountOfClicked list(clicked per day, week, month) )


    //////////////////////////////////////////
    //GetUserInfo(user_id) – admin [statistic]
    // Role = ADMIN
    // input: String email //??p Long userId
    // output: UserAdminResponseDto (
    // userId, email, role, status, registeredAt, activeAt,
    // amountOfCreated list(created per day, week, month)
    // amountOfClicked list(clicked per day, week, month) )
    public void GetUserInfo(String userEmail) {};
    //public void GetUserInfo(Long userId) {};

}
