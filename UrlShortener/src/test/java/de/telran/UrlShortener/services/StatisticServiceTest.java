package de.telran.UrlShortener.services;

import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.StatisticClickedUrlInterface;
import de.telran.UrlShortener.entities.StatisticGeneratedUrlInterface;
import de.telran.UrlShortener.entities.StatisticUserInterface;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import de.telran.UrlShortener.repositories.UrlRepository;
import de.telran.UrlShortener.repositories.UserRepository;
import de.telran.UrlShortener.utils.common.CommonUtils;
import de.telran.UrlShortener.utils.mapper.Mappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatisticServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private UrlRepository urlRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @Mock
    private CommonUtils utilsMock;

    @InjectMocks
    private StatisticService statisticServiceTest;

    private StatisticGeneratedUrlDto statisticGeneratedUrlDto;
    private Timestamp now;
    private MockGeneratedUrl mockGeneratedUrl1;
    private MockGeneratedUrl mockGeneratedUrl2;
    private MockClickedUrl mockClickedUrl1;
    private MockClickedUrl mockClickedUrl2;
    private MockUser mockUser1;
    private MockUser mockUser2;

    private class MockGeneratedUrl implements StatisticGeneratedUrlInterface {
        private String client;
        private Integer generated;
        private Timestamp createdAt;
        private String shortUrl;
        private String longUrl;
        public MockGeneratedUrl() {
            client = "client";
            generated = 11;
            createdAt = now;
            shortUrl = "google";
            longUrl = "https://www.google.com";
        }
        @Override
        public String getClient() { return client; }
        @Override
        public Integer getGenerated() { return generated; }
        @Override
        public Timestamp getCreatedAt() { return createdAt; }
        @Override
        public String getShortUrl() { return shortUrl; }
        @Override
        public String getLongUrl() { return longUrl; }
    }

    private class MockClickedUrl implements StatisticClickedUrlInterface {
        private String shortUrl;
        private Integer clicked;
        private String client;
        private Long id;
        private String longUrl;
        public MockClickedUrl() {
            shortUrl = "google";
            clicked = 41;
            client = "client";
            id = 1L;
            longUrl = "https://www.google.com";
        }
        @Override
        public String getClient() { return client; }
        @Override
        public Integer getClicked() { return clicked; }
        @Override
        public String getShortUrl() { return shortUrl; }
        @Override
        public Long getId() { return id; }
        @Override
        public String getLongUrl() { return longUrl; }
    }

    private class MockUser implements StatisticUserInterface {
        private Long userId;
        private String userEmail;
        private Long generatedAmount;
        private Long clickedAmount;
        private UserStatusEnum status;
        private UserRoleEnum role;
        private Timestamp registeredAt;
        private Timestamp lastActiveAt;
        private Timestamp updatedAt;

        public MockUser() {
            userId = 1L;
            userEmail = "user@example.com";
            generatedAmount = 6L;
            clickedAmount = 33L;
            status = UserStatusEnum.ACTIVE;
            role = UserRoleEnum.CLIENT;
            registeredAt = now;
            lastActiveAt = now;
            updatedAt = now;
        }
        @Override
        public Long getUserId() { return userId; }
        @Override
        public String getUserEmail() { return userEmail; }
        @Override
        public Long getGeneratedAmount() { return generatedAmount; }
        @Override
        public Long getClickedAmount() { return clickedAmount; }
        @Override
        public UserStatusEnum getStatus() { return status; }
        @Override
        public UserRoleEnum getRole() { return role; }
        @Override
        public Timestamp getRegisteredAt() { return registeredAt; }
        @Override
        public Timestamp getLastActiveAt() { return lastActiveAt; }
        @Override
        public Timestamp getUpdatedAt() { return updatedAt; }
    }

    @BeforeEach
    void setUp() {
        now = Timestamp.valueOf(LocalDateTime.now());

        statisticGeneratedUrlDto = new StatisticGeneratedUrlDto(
                "client",
                10,
                now,
                "google",
                "https://www.google.com"
        );

        mockGeneratedUrl1 = new MockGeneratedUrl();
        mockGeneratedUrl2 = new MockGeneratedUrl();

        mockClickedUrl1 = new MockClickedUrl();
        mockClickedUrl2 = new MockClickedUrl();

        mockUser1 = new MockUser();
        mockUser2 = new MockUser();
    }

    // email = null
    // period = null
    // top = null
    // detail = null
    // finds != null
    @Test
    void getGeneratedUrlsStatistic1() {
        StatisticGeneratingUrlRequestDto requestDto = new StatisticGeneratingUrlRequestDto(
                null,
                null,
                null,
                null
        );

        when(urlRepositoryMock.findGeneratedUrlsRegisteredNative(
                any(Timestamp.class), any(Integer.class)))
                .thenReturn(List.of(mockGeneratedUrl1,mockGeneratedUrl2));

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        when(mappersMock.convertToStatisticGeneratedUrlDto(
                any(StatisticGeneratedUrlInterface.class)))
                .thenReturn(any(StatisticGeneratedUrlDto.class));

        List<StatisticGeneratedUrlDto> res = statisticServiceTest.getGeneratedUrlsStatistic(requestDto);

        assertNotNull(res);
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredDetailsNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsIncludeAnonimDetailsNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredUserDetailsNative(
                        any(),
                        any(Timestamp.class),
                        any(Integer.class)
                );

        verify(urlRepositoryMock, times(1))
                .findGeneratedUrlsRegisteredNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsIncludeAnonimNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredUserNative(
                        any(),
                        any(Timestamp.class),
                        any(Integer.class)
                );

        verify(utilsMock, times(1))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(2))
                .convertToStatisticGeneratedUrlDto(any(StatisticGeneratedUrlInterface.class));
    }

    // email != null, size = 0
    // period = 0
    // top = 0
    // detail = false
    // finds != null
    @Test
    void getGeneratedUrlsStatistic2() {
        List<String> emails = new ArrayList<>();

        StatisticGeneratingUrlRequestDto requestDto = new StatisticGeneratingUrlRequestDto(
                emails,
                0,
                0,
                false
        );

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        when(urlRepositoryMock.findGeneratedUrlsIncludeAnonimNative(
                any(Timestamp.class), any(Integer.class)))
                .thenReturn(List.of(mockGeneratedUrl1,mockGeneratedUrl2));

        List<StatisticGeneratedUrlDto> res = statisticServiceTest.getGeneratedUrlsStatistic(requestDto);

        assertNotNull(res);
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredDetailsNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsIncludeAnonimDetailsNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredUserDetailsNative(
                        any(),
                        any(Timestamp.class),
                        any(Integer.class)
                );

        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(1))
                .findGeneratedUrlsIncludeAnonimNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredUserNative(
                        any(),
                        any(Timestamp.class),
                        any(Integer.class)
                );

        verify(utilsMock, times(1))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(2))
                .convertToStatisticGeneratedUrlDto(any(StatisticGeneratedUrlInterface.class));
    }

    // email != null, size > 0
    // period = 500
    // top = 200
    // detail = false
    // finds = null
    @Test
    void getGeneratedUrlsStatistic3() {
        List<String> emails = List.of("test3@example.com", "test1@example.com", "test2@example.com");
        StatisticGeneratingUrlRequestDto requestDto = new StatisticGeneratingUrlRequestDto(
                emails,
                500,
                200,
                false
        );

        when(urlRepositoryMock.findGeneratedUrlsRegisteredUserNative(
                any(),
                any(Timestamp.class), any(Integer.class)))
                .thenReturn(null);

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        List<StatisticGeneratedUrlDto> res = statisticServiceTest.getGeneratedUrlsStatistic(requestDto);

        assertNotNull(res);
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredDetailsNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsIncludeAnonimDetailsNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredUserDetailsNative(
                        any(),
                        any(Timestamp.class),
                        any(Integer.class)
                );

        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsIncludeAnonimNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(1))
                .findGeneratedUrlsRegisteredUserNative(
                        any(),
                        any(Timestamp.class),
                        any(Integer.class)
                );

        verify(utilsMock, times(1))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(0))
                .convertToStatisticGeneratedUrlDto(any(StatisticGeneratedUrlInterface.class));
    }

    // email != null, size = 0
    // period = 200
    // top = 100
    // detail = true
    // finds != null
    @Test
    void getGeneratedUrlsStatistic4() {
        List<String> emails = new ArrayList<>();
        StatisticGeneratingUrlRequestDto requestDto = new StatisticGeneratingUrlRequestDto(
                emails,
                200,
                100,
                true
        );

        when(urlRepositoryMock.findGeneratedUrlsIncludeAnonimDetailsNative(
                any(Timestamp.class), any(Integer.class)))
                .thenReturn(List.of(mockGeneratedUrl1,mockGeneratedUrl2));

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        when(mappersMock.convertToStatisticGeneratedUrlDto(
                any(StatisticGeneratedUrlInterface.class)))
                .thenReturn(any(StatisticGeneratedUrlDto.class));

        List<StatisticGeneratedUrlDto> res = statisticServiceTest.getGeneratedUrlsStatistic(requestDto);

        assertNotNull(res);
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredDetailsNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(1))
                .findGeneratedUrlsIncludeAnonimDetailsNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredUserDetailsNative(
                        any(),
                        any(Timestamp.class),
                        any(Integer.class)
                );

        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsIncludeAnonimNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredUserNative(
                        any(),
                        any(Timestamp.class),
                        any(Integer.class)
                );

        verify(utilsMock, times(2))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(2))
                .convertToStatisticGeneratedUrlDto(any(StatisticGeneratedUrlInterface.class));
    }

    // email != null, size > 0
    // period = 200
    // top = 100
    // detail = true
    // finds = null
    @Test
    void getGeneratedUrlsStatistic5() {
        List<String> emails = (List.of("test3@example.com", "test1@example.com", "test2@example.com"));
        StatisticGeneratingUrlRequestDto requestDto = new StatisticGeneratingUrlRequestDto(
                emails,
                200,
                100,
                true
        );

        when(urlRepositoryMock.findGeneratedUrlsRegisteredUserDetailsNative(
                any(),
                any(Timestamp.class),
                any(Integer.class)))
                .thenReturn(null);

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        List<StatisticGeneratedUrlDto> res = statisticServiceTest.getGeneratedUrlsStatistic(requestDto);

        assertNotNull(res);
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredDetailsNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsIncludeAnonimDetailsNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(1))
                .findGeneratedUrlsRegisteredUserDetailsNative(
                        any(),
                        any(Timestamp.class),
                        any(Integer.class)
                );

        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsIncludeAnonimNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredUserNative(
                        any(),
                        any(Timestamp.class),
                        any(Integer.class)
                );

        verify(utilsMock, times(2))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(0))
                .convertToStatisticGeneratedUrlDto(any(StatisticGeneratedUrlInterface.class));
    }

    // email = null
    // period = 200
    // top = 100
    // detail = true
    // finds = null
    @Test
    void getGeneratedUrlsStatistic6() {
        StatisticGeneratingUrlRequestDto requestDto = new StatisticGeneratingUrlRequestDto(
                null,
                200,
                100,
                true
        );

        when(urlRepositoryMock.findGeneratedUrlsRegisteredDetailsNative(
                any(Timestamp.class), any(Integer.class)))
                .thenReturn(null);

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        List<StatisticGeneratedUrlDto> res = statisticServiceTest.getGeneratedUrlsStatistic(requestDto);

        assertNotNull(res);
        verify(urlRepositoryMock, times(1))
                .findGeneratedUrlsRegisteredDetailsNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsIncludeAnonimDetailsNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredUserDetailsNative(
                        any(),
                        any(Timestamp.class),
                        any(Integer.class)
                );

        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsIncludeAnonimNative(
                        any(Timestamp.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findGeneratedUrlsRegisteredUserNative(
                        any(),
                        any(Timestamp.class),
                        any(Integer.class)
                );

        verify(utilsMock, times(2))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(0))
                .convertToStatisticGeneratedUrlDto(any(StatisticGeneratedUrlInterface.class));
    }

    // email = null
    // period = null
    // top = null
    // not_popular = null
    // finds != null
    @Test
    void getClickedUrlsStatistic1() {
        StatisticClickedUrlRequestDto requestDto = new StatisticClickedUrlRequestDto(
                null,
                null,
                null,
                null
        );

        when(urlRepositoryMock.findClickedUrlsRegisteredNative(
                any(Timestamp.class), any(String.class), any(Integer.class)))
                .thenReturn(List.of(mockClickedUrl1,mockClickedUrl2));

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        when(mappersMock.convertToStatisticClickedUrlDto(
                any(StatisticClickedUrlInterface.class)))
                .thenReturn(any(StatisticClickedUrlDto.class));

        List<StatisticClickedUrlDto> res = statisticServiceTest.getClickedUrlsStatistic(requestDto);

        assertNotNull(res);
        verify(urlRepositoryMock, times(1))
                .findClickedUrlsRegisteredNative(
                        any(Timestamp.class), any(String.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findPopularClickedUrlsIncludeAnonimNative(
                        any(Timestamp.class), any(String.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findClickedUrlsRegisteredUserNative(
                        any(),
                        any(Timestamp.class),
                        any(String.class),
                        any(Integer.class)
                );

        verify(utilsMock, times(1))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(2))
                .convertToStatisticClickedUrlDto(any(StatisticClickedUrlInterface.class));
    }

    // email != null, size > 0
    // period = 200
    // top = 10
    // not_popular = true
    // finds = null
    @Test
    void getClickedUrlsStatistic2() {
        List<String> emails = (List.of("test3@example.com", "test1@example.com", "test2@example.com"));

                StatisticClickedUrlRequestDto requestDto = new StatisticClickedUrlRequestDto(
                emails,
                200,
                10,
                true
        );

        when(urlRepositoryMock.findClickedUrlsRegisteredUserNative(
                any(),
                any(Timestamp.class),
                any(String.class),
                any(Integer.class)))
                .thenReturn(null);

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        List<StatisticClickedUrlDto> res = statisticServiceTest.getClickedUrlsStatistic(requestDto);

        assertNotNull(res);
        verify(urlRepositoryMock, times(0))
                .findClickedUrlsRegisteredNative(
                        any(Timestamp.class), any(String.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findPopularClickedUrlsIncludeAnonimNative(
                        any(Timestamp.class), any(String.class), any(Integer.class));
        verify(urlRepositoryMock, times(1))
                .findClickedUrlsRegisteredUserNative(
                        any(),
                        any(Timestamp.class),
                        any(String.class),
                        any(Integer.class)
                );

        verify(utilsMock, times(2))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(0))
                .convertToStatisticClickedUrlDto(any(StatisticClickedUrlInterface.class));
    }

    // email != null, size = 0
    // period = 0
    // top = 0
    // not_popular = false
    // finds = null
    @Test
    void getClickedUrlsStatistic3() {
        List<String> emails = new ArrayList<>();

        StatisticClickedUrlRequestDto requestDto = new StatisticClickedUrlRequestDto(
                emails,
                0,
                0,
                false
        );

        when(urlRepositoryMock.findPopularClickedUrlsIncludeAnonimNative(
                any(Timestamp.class), any(String.class), any(Integer.class)))
                .thenReturn(null);

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        List<StatisticClickedUrlDto> res = statisticServiceTest.getClickedUrlsStatistic(requestDto);

        assertNotNull(res);
        verify(urlRepositoryMock, times(0))
                .findClickedUrlsRegisteredNative(
                        any(Timestamp.class), any(String.class), any(Integer.class));
        verify(urlRepositoryMock, times(1))
                .findPopularClickedUrlsIncludeAnonimNative(
                        any(Timestamp.class), any(String.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findClickedUrlsRegisteredUserNative(
                        any(),
                        any(Timestamp.class),
                        any(String.class),
                        any(Integer.class)
                );

        verify(utilsMock, times(1))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(0))
                .convertToStatisticClickedUrlDto(any(StatisticClickedUrlInterface.class));
    }

    // email = null
    // period = 500
    // top = null
    // not_popular = null
    // finds != null
    @Test
    void getClickedUrlsStatistic4() {
        StatisticClickedUrlRequestDto requestDto = new StatisticClickedUrlRequestDto(
                null,
                500,
                null,
                null
        );

        when(urlRepositoryMock.findClickedUrlsRegisteredNative(
                any(Timestamp.class), any(String.class), any(Integer.class)))
                .thenReturn(List.of(mockClickedUrl1,mockClickedUrl2));

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        when(mappersMock.convertToStatisticClickedUrlDto(
                any(StatisticClickedUrlInterface.class)))
                .thenReturn(any(StatisticClickedUrlDto.class));

        List<StatisticClickedUrlDto> res = statisticServiceTest.getClickedUrlsStatistic(requestDto);

        assertNotNull(res);
        verify(urlRepositoryMock, times(1))
                .findClickedUrlsRegisteredNative(
                        any(Timestamp.class), any(String.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findPopularClickedUrlsIncludeAnonimNative(
                        any(Timestamp.class), any(String.class), any(Integer.class));
        verify(urlRepositoryMock, times(0))
                .findClickedUrlsRegisteredUserNative(
                        any(),
                        any(Timestamp.class),
                        any(String.class),
                        any(Integer.class)
                );

        verify(utilsMock, times(1))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(2))
                .convertToStatisticClickedUrlDto(any(StatisticClickedUrlInterface.class));
    }

    // email = null
    // role = null
    // status = null
    // period = null
    // top = null
    // finds != null
    @Test
    void getUsersStatistic1() {
        StatisticUserRequestDto requestDto = new StatisticUserRequestDto(
                null,
                null,
                null,
                null,
                null
        );

        when(userRepositoryMock.findAllUsersByRoleAndStatusNative(
                any(),
                any(),
                any(Timestamp.class),
                any(Integer.class)))
                .thenReturn(List.of(mockUser1,mockUser2));

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        when(mappersMock.convertToStatisticUserResponseDto(
                any(StatisticUserInterface.class)))
                .thenReturn(any(StatisticUserResponseDto.class));

        List<StatisticUserResponseDto> res = statisticServiceTest.getUsersStatistic(requestDto);

        assertNotNull(res);
        verify(userRepositoryMock, times(1))
                .findAllUsersByRoleAndStatusNative(
                        any(),
                        any(),
                        any(Timestamp.class),
                        any(Integer.class));

        verify(userRepositoryMock, times(0))
                .findSpecifiedUsersByRoleAndStatusNative(
                        any(),
                        any(),
                        any(),
                        any(Timestamp.class),
                        any(Integer.class));

        verify(utilsMock, times(1))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(2))
                .convertToStatisticUserResponseDto(any(StatisticUserInterface.class));
    }

    // email size = 0
    // role size = 0
    // status size = 0
    // period = 0
    // top = 0
    // finds != null
    @Test
    void getUsersStatistic2() {
        List<String> emails = new ArrayList<>();

        StatisticUserRequestDto requestDto = new StatisticUserRequestDto(
                emails,
                null,
                null,
                0,
                0
        );

        when(userRepositoryMock.findAllUsersByRoleAndStatusNative(
                any(),
                any(),
                any(Timestamp.class),
                any(Integer.class)))
                .thenReturn(List.of(mockUser1,mockUser2));

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        when(mappersMock.convertToStatisticUserResponseDto(
                any(StatisticUserInterface.class)))
                .thenReturn(any(StatisticUserResponseDto.class));

        List<StatisticUserResponseDto> res = statisticServiceTest.getUsersStatistic(requestDto);

        assertNotNull(res);
        verify(userRepositoryMock, times(1))
                .findAllUsersByRoleAndStatusNative(
                        any(),
                        any(),
                        any(Timestamp.class),
                        any(Integer.class));

        verify(userRepositoryMock, times(0))
                .findSpecifiedUsersByRoleAndStatusNative(
                        any(),
                        any(),
                        any(),
                        any(Timestamp.class),
                        any(Integer.class));

        verify(utilsMock, times(1))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(2))
                .convertToStatisticUserResponseDto(any(StatisticUserInterface.class));
    }

    // email size > 0
    // role size > 0
    // status size > 0
    // period = 200
    // top = 100
    // finds = null
    @Test
    void getUsersStatistic3() {
        List<String> emails = (List.of("test3@example.com", "test1@example.com", "test2@example.com"));
        UserStatusEnum status = UserStatusEnum.ACTIVE;
        UserRoleEnum role = UserRoleEnum.CLIENT;
        List<UserStatusEnum> statuses = List.of(status, status);
        List<UserRoleEnum> roles = List.of(role);

        StatisticUserRequestDto requestDto = new StatisticUserRequestDto(
                emails,
                roles,
                statuses,
                200,
                100
        );

        when(userRepositoryMock.findSpecifiedUsersByRoleAndStatusNative(
                any(),
                any(),
                any(),
                any(Timestamp.class),
                any(Integer.class)))
                .thenReturn(null);

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        List<StatisticUserResponseDto> res = statisticServiceTest.getUsersStatistic(requestDto);

        assertNotNull(res);
        verify(userRepositoryMock, times(0))
                .findAllUsersByRoleAndStatusNative(
                        any(),
                        any(),
                        any(Timestamp.class),
                        any(Integer.class));

        verify(userRepositoryMock, times(1))
                .findSpecifiedUsersByRoleAndStatusNative(
                        any(),
                        any(),
                        any(),
                        any(Timestamp.class),
                        any(Integer.class));

        verify(utilsMock, times(2))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(0))
                .convertToStatisticUserResponseDto(any(StatisticUserInterface.class));
    }

    // email size > 0
    // role size > 0
    // status size > 0
    // period = 500
    // top = 200
    // finds != null
    @Test
    void getUsersStatistic4() {
        List<String> emails = (List.of("test3@example.com", "test1@example.com", "test2@example.com"));
        UserStatusEnum status = UserStatusEnum.ACTIVE;
        UserRoleEnum role = UserRoleEnum.CLIENT;
        List<UserStatusEnum> statuses = List.of(status, status);
        List<UserRoleEnum> roles = List.of(role, role);
        StatisticUserRequestDto requestDto = new StatisticUserRequestDto(
                emails,
                roles,
                statuses,
                500,
                200
        );

        when(userRepositoryMock.findSpecifiedUsersByRoleAndStatusNative(
                any(),
                any(),
                any(),
                any(Timestamp.class),
                any(Integer.class)))
                .thenReturn(List.of(mockUser1,mockUser2));

        when(utilsMock.getCurrentTimestampShiftDays(any(Integer.class)))
                .thenReturn(now);

        when(mappersMock.convertToStatisticUserResponseDto(
                any(StatisticUserInterface.class)))
                .thenReturn(any(StatisticUserResponseDto.class));

        List<StatisticUserResponseDto> res = statisticServiceTest.getUsersStatistic(requestDto);

        assertNotNull(res);
        verify(userRepositoryMock, times(0))
                .findAllUsersByRoleAndStatusNative(
                        any(),
                        any(),
                        any(Timestamp.class),
                        any(Integer.class));

        verify(userRepositoryMock, times(1))
                .findSpecifiedUsersByRoleAndStatusNative(
                        any(),
                        any(),
                        any(),
                        any(Timestamp.class),
                        any(Integer.class));

        verify(utilsMock, times(1))
                .getCurrentTimestampShiftDays(any(Integer.class));
        verify(mappersMock, times(2))
                .convertToStatisticUserResponseDto(any(StatisticUserInterface.class));
    }
}
