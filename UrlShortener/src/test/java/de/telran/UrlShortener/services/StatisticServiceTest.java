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