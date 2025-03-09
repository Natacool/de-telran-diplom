package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import de.telran.UrlShortener.services.StatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticController.class)
class StatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticService statisticServiceMock;

    private Timestamp now;

    @BeforeEach
    void setUp() {
        now = Timestamp.valueOf(LocalDateTime.now());
    }

    @Test
    void getGeneratedUrlsStat() throws Exception {
        when(statisticServiceMock.getGeneratedUrlsStatistic(
                any(StatisticGeneratingUrlRequestDto.class))).thenReturn(List.of(
                new StatisticGeneratedUrlDto(
                        "client",
                        10,
                        now,
                        "google",
                        "https://www.google.com"
                )));

        this.mockMvc.perform(get("/stat/generated_urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "userEmails": ["jack@example.com"],
                            "periodDays": "90",
                            "limitTop": "20",
                            "details": "true"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..client").exists())
                .andExpect(jsonPath("$..client").value("client"))
                .andExpect(jsonPath("$..generated").exists())
                .andExpect(jsonPath("$..generated").value(10))
                .andExpect(jsonPath("$..createdAt").exists())
                .andExpect(jsonPath("$..createdAt").isNotEmpty())
                .andExpect(jsonPath("$..shortUrl").exists())
                .andExpect(jsonPath("$..shortUrl").value("google"))
                .andExpect(jsonPath("$..longUrl").exists())
                .andExpect(jsonPath("$..longUrl").value("https://www.google.com"))
        ;
    }

    @Test
    void getClickedUrlsStat() throws Exception {
        when(statisticServiceMock.getClickedUrlsStatistic(
                any(StatisticClickedUrlRequestDto.class))).thenReturn(List.of(
                new StatisticClickedUrlDto(
                        "google",
                        12,
                        "client",
                        "https://www.google.com")
        ));

        this.mockMvc.perform(get("/stat/clicked_urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "userId": "2",
                            "periodDays": "90",
                            "descent": "true",
                            "limitTop": "12"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..shortUrl").exists())
                .andExpect(jsonPath("$..shortUrl").value("google"))
                .andExpect(jsonPath("$..clicked").exists())
                .andExpect(jsonPath("$..clicked").isNotEmpty())
                .andExpect(jsonPath("$..clicked").value(12))
                .andExpect(jsonPath("$..client").exists())
                .andExpect(jsonPath("$..client").value("client"))
                .andExpect(jsonPath("$..longUrl").exists())
                .andExpect(jsonPath("$..longUrl").value("https://www.google.com"))
        ;
    }

    @Test
    void getUsersStat() throws Exception {
        when(statisticServiceMock.getUsersStatistic(
                any(StatisticUserRequestDto.class))).thenReturn(List.of(
                new StatisticUserResponseDto(
                        2L,
                        "test@example.com",
                        13L,
                        65L,
                        UserStatusEnum.ACTIVE,
                        UserRoleEnum.CLIENT,
                        Timestamp.valueOf(LocalDateTime.now().minusMonths(12)),
                        Timestamp.valueOf(LocalDateTime.now().minusDays(1)),
                        Timestamp.valueOf(LocalDateTime.now().minusMonths(1))
                )));

        this.mockMvc.perform(get("/stat/users_info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {

                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..userId").exists())
                .andExpect(jsonPath("$..userId").value(2))
                .andExpect(jsonPath("$..userEmail").exists())
                .andExpect(jsonPath("$..userEmail").value("test@example.com"))
                .andExpect(jsonPath("$..role").exists())
                .andExpect(jsonPath("$..role").value("CLIENT"))
                .andExpect(jsonPath("$..status").exists())
                .andExpect(jsonPath("$..status").value("ACTIVE"))
                .andExpect(jsonPath("$..registeredAt").exists())
                .andExpect(jsonPath("$..lastActiveAt").exists())
                .andExpect(jsonPath("$..updatedAt").exists())
                .andExpect(jsonPath("$..generatedAmount").exists())
                .andExpect(jsonPath("$..generatedAmount").value(13))
                .andExpect(jsonPath("$..clickedAmount").exists())
                .andExpect(jsonPath("$..clickedAmount").value(65))
        ;

    }

}