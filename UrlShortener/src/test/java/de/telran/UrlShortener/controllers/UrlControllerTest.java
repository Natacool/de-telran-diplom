package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.UrlCopyEntityDto;
import de.telran.UrlShortener.services.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UrlController.class)
class UrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlServiceMock;


/*

    @Test
    void generateUrl() {
    }

    @Test
    void redirectUrlBody() {
    }

    @Test
    void redirectUrl() {
    }

    @Test
    void redirectErrMsg() {
    }

    @Test
    void deleteByShortUrl() {
    }

 */
    @Test
    void getAllUrls() throws Exception {
        when(urlServiceMock.getAllUrls()).thenReturn(List.of(new UrlCopyEntityDto(
                1L,
                "google",
                "https://www.google.com",
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                0L,
                7L,
                0L,
                null,
                false)));

        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..urlId").exists())
                .andExpect(jsonPath("$..urlId").value(1))
                .andExpect(jsonPath("$..shortUrl").value("google"))
                .andExpect(jsonPath("$..longUrl").value("https://www.google.com"))
                //.andExpect(jsonPath("$..clickedAt").value(null))
                .andExpect(jsonPath("$..clickAmount").value(0))
                .andExpect(jsonPath("$..deleteAfterDays").value(7))
                .andExpect(jsonPath("$..userId").value(0))
                .andExpect(jsonPath("$..isFavorite").value(false))
        ;
    }

/*
    @Test
    void getLongUrl() {
    }

    @Test
    void getShortUrl() {
    }

    @Test
    void updateUrlDeleteTimer() {
    }
*/
}