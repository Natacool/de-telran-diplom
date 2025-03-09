package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import de.telran.UrlShortener.services.StatisticService;
import org.junit.jupiter.api.AfterEach;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticController.class)
class StatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticService statisticServiceMock;

/*

    @BeforeAll
    void setUp() {
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    void tearDown() {
    }
*/
    @Test
    void getGeneratedUrlsStat() throws Exception {
        when(statisticServiceMock.getGeneratedUrlsStatistic(
                any(StatisticGeneratingUrlRequestDto.class))).thenReturn(List.of(
                new StatisticGeneratingUrlResponseDto(
                        Timestamp.valueOf(LocalDateTime.now().minusMonths(12)),
                        1L,
                        "google",
                        "https://www.google.com")
                ));

        this.mockMvc.perform(get("/stat/generated_urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "userId": "1",
                            "periodDays": "90",
                            "sortByUser": "false",
                            "descent": "true",
                            "limitTop": "15"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..userId").exists())
                .andExpect(jsonPath("$..userId").value(1))
                .andExpect(jsonPath("$..shortUrl").exists())
                .andExpect(jsonPath("$..shortUrl").value("google"))
                .andExpect(jsonPath("$..longUrl").exists())
                .andExpect(jsonPath("$..longUrl").value("https://www.google.com"))
                .andExpect(jsonPath("$..createdAt").exists())
                .andExpect(jsonPath("$..createdAt").isNotEmpty())
        ;
    }

    @Test
    void getClickedUrlsStat() throws Exception {
        when(statisticServiceMock.getClickedUrlsStatistic(
                any(StatisticClickedUrlRequestDto.class))).thenReturn(List.of(
                new StatisticClickedUrlResponseDto(
                        "google",
                        12L,
                        2L,
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
                .andExpect(jsonPath("$..clickedAmount").exists())
                .andExpect(jsonPath("$..clickedAmount").isNotEmpty())
                .andExpect(jsonPath("$..clickedAmount").value(12))
                .andExpect(jsonPath("$..userId").exists())
                .andExpect(jsonPath("$..userId").value(2))
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
                        UserRoleEnum.CLIENT,
                        UserStatusEnum.ACTIVE,
                        Timestamp.valueOf(LocalDateTime.now().minusMonths(12)),
                        Timestamp.valueOf(LocalDateTime.now().minusDays(1)),
                        Timestamp.valueOf(LocalDateTime.now().minusMonths(1)),
                        10L,
                        100L)
        ));



//                            "userRoles": [],
//                            "userRoles": [{"CLIENT", "ADMIN"}],
//                            "userStatuses": [{"ACTIVE"}],
//                            "periodGenerated": "30",
//                            "periodClicked": "7",
//                            "onlyUserInfo": "12"
        //"userEmail": "test@example.com",
        //"periodGenerated": "30"
        //"onlyUserInfo": "12"

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
                .andExpect(jsonPath("$..generatedAmount").value(10))
                .andExpect(jsonPath("$..clickedAmount").exists())
                .andExpect(jsonPath("$..clickedAmount").value(100))

//                .andExpect(jsonPath("$..shortUrl").value("google"))
//                .andExpect(jsonPath("$..clickedAmount").exists())
//                .andExpect(jsonPath("$..clickedAmount").isNotEmpty())
//                .andExpect(jsonPath("$..clickedAmount").value(12))
//                .andExpect(jsonPath("$..userId").exists())
//                .andExpect(jsonPath("$..userId").value(2))
//                .andExpect(jsonPath("$..longUrl").exists())
//                .andExpect(jsonPath("$..longUrl").value("https://www.google.com"))
        ;

    }

}