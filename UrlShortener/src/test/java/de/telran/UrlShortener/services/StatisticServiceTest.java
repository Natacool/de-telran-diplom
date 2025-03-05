package de.telran.UrlShortener.services;

import de.telran.UrlShortener.dtos.StatisticClickedUrlRequestDto;
import de.telran.UrlShortener.dtos.StatisticClickedUrlResponseDto;
import de.telran.UrlShortener.dtos.StatisticGeneratingUrlRequestDto;
import de.telran.UrlShortener.dtos.StatisticGeneratingUrlResponseDto;
import de.telran.UrlShortener.repositories.UrlRepository;
import de.telran.UrlShortener.repositories.UserRepository;
import de.telran.UrlShortener.utils.mapper.Mappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StatisticServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private UrlRepository urlRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @InjectMocks
    private StatisticService statisticServiceTest;

    @BeforeEach
    void setUp() {
    }


/*
        String periodDays = "365";
        if (requestDto.getPeriodDays() != null && requestDto.getPeriodDays() > 0){
            periodDays = requestDto.getPeriodDays().toString();
        }

        String orderBy = "Urls.CreatedAt";
        if (requestDto.getSortByUser() != null && requestDto.getSortByUser() == true){
            orderBy = "Urls.UserID";
        }

        String descent = "";
        if (requestDto.getDescent() != null && requestDto.getDescent() == true){
            descent = "DESC";
        }

        String limitTop = "";
        if (requestDto.getLimitTop() != null && requestDto.getLimitTop() > 0){
            limitTop = " limit " + requestDto.getLimitTop();
        }


 */






    @Test
    void getGeneratedUrlsStatistic() {
        //List<StatisticGeneratingUrlResponseDto> getGeneratedUrlsStatistic(StatisticGeneratingUrlRequestDto requestDto
    }















    @Test
    void getClickedUrlsStatistic() {
        //List<StatisticClickedUrlResponseDto> getClickedUrlsStatistic(StatisticClickedUrlRequestDto requestDto
    }














    @Test
    void getUsersStatistic() {
        //List<StatisticUserResponseDto> getUsersStatistic(StatisticUserRequestDto requestUsers
    }
}