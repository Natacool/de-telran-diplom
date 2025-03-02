package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.services.StatisticService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@WebMvcTest(StatisticController.class)
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
/*

    @Test
    void getGeneratedUrlsStat() {
        when(statisticServiceMock.getGeneratedUrlsStatistic()).thenReturn()
    }

    @Test
    void getClickedUrlsStat() {
    }

    @Test
    void getUsersStat() {

    }
*/
}