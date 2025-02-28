package de.telran.UrlShortener.mapper;

import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.UrlEntity;
import de.telran.UrlShortener.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mappers {
    private final ModelMapper modelMapper;

    public UserCopyEntityDto convertToUserCopyDto(UserEntity userEntity) {
        if(userEntity==null) return new UserCopyEntityDto();

        UserCopyEntityDto userCopyDto = modelMapper.map(userEntity, UserCopyEntityDto.class);
        userCopyDto.setPasswordHash("*****");

        if (userCopyDto.getUpdatedAt() == null)
            userCopyDto.setUpdatedAt(userCopyDto.getRegisteredAt());

        return userCopyDto;
    }

    public UrlCopyEntityDto convertToUrlCopy(UrlEntity urlEntity) {
        if(urlEntity == null) return new UrlCopyEntityDto();

        UrlCopyEntityDto urlCopyDto = modelMapper.map(urlEntity, UrlCopyEntityDto.class);

        return urlCopyDto;
    }


    public UserResponseDto convertToUserResponseDto(UserEntity userEntity) {
        if(userEntity==null) return new UserResponseDto();

        UserResponseDto userResponseDto = modelMapper.map(userEntity, UserResponseDto.class);
        userResponseDto.setPasswordHash("*****");

        if (userResponseDto.getUpdatedAt() == null)
            userResponseDto.setUpdatedAt(userResponseDto.getRegisteredAt());

        return userResponseDto;
    }

    public StatisticClickedUrlResponseDto convertToStatisticClickedUrlResponseDto(UrlEntity urlEntity) {
        if(urlEntity == null) return new StatisticClickedUrlResponseDto();

        //StatisticClickedUrlResponseDto clickedUrlsDto = modelMapper.map(urlEntity, StatisticClickedUrlResponseDto.class);
        StatisticClickedUrlResponseDto clickedUrlsDto = new StatisticClickedUrlResponseDto();
        clickedUrlsDto.setShortUrl(urlEntity.getShortUrlId());
        clickedUrlsDto.setClickedAmount(urlEntity.getClickAmount());
        //clickedUrlsDto.setUserId(urlEntity.getUser().getUserId());
        clickedUrlsDto.setLongUrl(urlEntity.getLongUrl());

        return clickedUrlsDto;
    }

    public StatisticGeneratingUrlResponseDto convertToStatisticGeneratingUrlResponseDto(UrlEntity urlEntity) {
        if(urlEntity == null) return new StatisticGeneratingUrlResponseDto();

        //StatisticGeneratingUrlResponseDto GeneratingUrlsDto = modelMapper.map(urlEntity, StatisticGeneratingUrlResponseDto.class);
        StatisticGeneratingUrlResponseDto GeneratingUrlsDto = new StatisticGeneratingUrlResponseDto();
        GeneratingUrlsDto.setCreatedAt(urlEntity.getCreatedAt());
        //GeneratingUrlsDto.setUserId(urlEntity.getUser().getUserId());
        GeneratingUrlsDto.setShortUrl(urlEntity.getShortUrlId());
        GeneratingUrlsDto.setLongUrl(urlEntity.getLongUrl());

        return GeneratingUrlsDto;
    }

    public StatisticUserResponseDto convertToStatisticUserResponseDto(UserEntity userEntity) {
        if(userEntity == null) return new StatisticUserResponseDto();

        //StatisticUserResponseDto usersDto = modelMapper.map(userEntity, StatisticUserResponseDto.class);
        StatisticUserResponseDto usersDto = new StatisticUserResponseDto();
        //usersDto.setUserId(userEntity.getUserId());
        usersDto.setUserEmail(userEntity.getEmail());
        usersDto.setRole(userEntity.getRole());
        usersDto.setStatus(userEntity.getStatus());

        usersDto.setRegisteredAt(userEntity.getRegisteredAt());
        usersDto.setLastActiveAt(userEntity.getLastActiveAt());
        usersDto.setUpdatedAt(userEntity.getUpdatedAt());
        usersDto.setGeneratedAmount(0L);
        usersDto.setClickedAmount(0L);

        return usersDto;
    }
}
