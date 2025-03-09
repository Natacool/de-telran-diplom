package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.LongUrlDto;
import de.telran.UrlShortener.dtos.ShortUrlIdDto;
import de.telran.UrlShortener.dtos.UrlCopyEntityDto;
import de.telran.UrlShortener.dtos.UrlRequestUpdateDeleteTimerDto;
import de.telran.UrlShortener.services.UrlService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlController.class)
class UrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlServiceMock;

    ////////////////////////////////////////////////////////////////////
    // GENERATE SHORT URL
    @Test
    void generateUrl() throws Exception {
        when(urlServiceMock.getGeneratedUrl(any(LongUrlDto.class))).thenReturn(
                "google"
        );
        this.mockMvc.perform(post("/c")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "url": "https://www.google.com"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("google"))
        ;
    }

    @Test
    void generateUrlNull() throws Exception {
        when(urlServiceMock.getGeneratedUrl(any(LongUrlDto.class))).thenReturn(
                null
        );
        this.mockMvc.perform(post("/c")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "url": "https://www.google.com"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void generateUrlEmpty() throws Exception {
        when(urlServiceMock.getGeneratedUrl(any(LongUrlDto.class))).thenReturn(
                ""
        );
        this.mockMvc.perform(post("/c")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "url": "https://www.google.com"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    ////////////////////////////////////////////////////////////////////
    // REDIRECT URL body
    @Test
    void redirectUrlBody() throws Exception {
        when(urlServiceMock.getRedirectUrl(any(String.class))).thenReturn(
                "https://www.google.com"
        );

        this.mockMvc.perform(get("/x")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "urlId": "google"
                        }
                        """
                        ))
                .andDo(print())
                //.andExpect(status().isMovedTemporarily()) // DEPRECATED, but status is 302
                .andExpect(redirectedUrl("https://www.google.com"))
        ;
    }

    @Test
    void redirectUrlBodyNullUrl() throws Exception {
        when(urlServiceMock.getRedirectUrl(any(String.class))).thenReturn(
                null
        );

        this.mockMvc.perform(get("/x")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "urlId": "google"
                        }
                        """
                        ))
                .andDo(print())
                //.andExpect(status().isMovedTemporarily()) // DEPRECATED, but status is 302
                .andExpect(redirectedUrl("/wrong/url/google"))
        ;
    }

    @Test
    void redirectUrlBodyEmptyUrl() throws Exception {
        when(urlServiceMock.getRedirectUrl(any(String.class))).thenReturn(
                ""
        );

        this.mockMvc.perform(get("/x")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "urlId": "google"
                        }
                        """
                        ))
                .andDo(print())
                //.andExpect(status().isMovedTemporarily()) .. DEPRECATED, but status is 302
                .andExpect(redirectedUrl("/wrong/url/google"))
        ;
    }

    ////////////////////////////////////////////////////////////////////
    // REDIRECT URL
    @Test
    void redirectUrl() throws Exception {
        when(urlServiceMock.getRedirectUrl(any(String.class))).thenReturn(
                "https://www.google.com"
        );

        this.mockMvc.perform(get("/{urlId}", "google"))
                .andDo(print())
                .andExpect(redirectedUrl("https://www.google.com"))
        ;
    }

    @Test
    void redirectUrlNull() throws Exception {
        when(urlServiceMock.getRedirectUrl(any(String.class))).thenReturn(
                null
        );

        this.mockMvc.perform(get("/{urlId}", "google"))
                .andDo(print())
                .andExpect(redirectedUrl("/wrong/url/google"))
        ;
    }

    @Test
    void redirectUrlEmpty() throws Exception {
        when(urlServiceMock.getRedirectUrl(any(String.class))).thenReturn(
                ""
        );

        this.mockMvc.perform(get("/{urlId}", "google"))
                .andDo(print())
                .andExpect(redirectedUrl("/wrong/url/google"))
        ;
    }

    ////////////////////////////////////////////////////////////////////
    // REDIRECT TO ERROR
    @Test
    void redirectErrMsg() throws Exception {
        this.mockMvc.perform(get("/wrong/url/")
                .param("urlId", "google"))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    ////////////////////////////////////////////////////////////////////
    // DELETE by SHORT URL

    @Test
    void deleteByShortUrlTrue() throws Exception {
        when(urlServiceMock.deleteByShortUrl(any(ShortUrlIdDto.class))).thenReturn(true);

        this.mockMvc.perform(delete("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "urlId": "google"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("URL: 'google' deleted"))
        ;
    }

    @Test
    void deleteByShortUrlFalse() throws Exception {
        when(urlServiceMock.deleteByShortUrl(any(ShortUrlIdDto.class))).thenReturn(false);

        this.mockMvc.perform(delete("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "urlId": "google"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: URL: 'google' NOT deleted"))
        ;
    }

    ////////////////////////////////////////////////////////////////////
    // GET ALL URLs
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
                false)
                ));

        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..urlId").exists())
                .andExpect(jsonPath("$..urlId").value(1))
                .andExpect(jsonPath("$..shortUrl").value("google"))
                .andExpect(jsonPath("$..longUrl").value("https://www.google.com"))
                .andExpect(jsonPath("$..clickAmount").value(0))
                .andExpect(jsonPath("$..deleteAfterDays").value(7))
                .andExpect(jsonPath("$..userId").value(0))
                .andExpect(jsonPath("$..isFavorite").value(false))
        ;
    }

    ////////////////////////////////////////////////////////////////////
    // GET LONG URL
    @Test
    void getLongUrl() throws Exception {
        when(urlServiceMock.getLongUrl(any(String.class))).thenReturn(
                "https://www.google.com"
        );

        this.mockMvc.perform(get("/long")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "urlId": "google"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("https://www.google.com"))
        ;
    }

    @Test
    void getLonUrlNull() throws Exception {
        when(urlServiceMock.getShortUrl(any(String.class))).thenReturn(
                null
        );

        this.mockMvc.perform(get("/short")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "urlId": "google"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    void getLongUrlEmpty() throws Exception {
        when(urlServiceMock.getLongUrl(any(String.class))).thenReturn(
                ""
        );

        this.mockMvc.perform(get("/long")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "urlId": "google"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(""))
        ;
    }

    ////////////////////////////////////////////////////////////////////
    // GET SHORT URL
    @Test
    void getShortUrl() throws Exception {
        when(urlServiceMock.getShortUrl(any(String.class))).thenReturn(
                "google"
        );

        this.mockMvc.perform(get("/short")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "url": "https://www.google.com"
                        }
                        """
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("google"))
        ;
    }

    @Test
    void getShortUrlNull() throws Exception {
        when(urlServiceMock.getShortUrl(any(String.class))).thenReturn(
                null
        );

        this.mockMvc.perform(get("/short")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "url": "https://www.google.com"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    void getShortUrlEmpty() throws Exception {
        when(urlServiceMock.getShortUrl(any(String.class))).thenReturn(
                ""
        );

        this.mockMvc.perform(get("/short")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "url": "https://www.google.com"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(""))
        ;
    }

    ////////////////////////////////////////////////////////////////////
    // UPDATE URL DELETE TIMER
    @Test
    void updateUrlDeleteTimer() throws Exception {
        when(urlServiceMock.updateCleanTimer(any(UrlRequestUpdateDeleteTimerDto.class)))
                .thenReturn(new UrlCopyEntityDto(
                    1L,
                    "google",
                    "https://www.google.com",
                    Timestamp.valueOf(LocalDateTime.now()),
                    null,
                    0L,
                    30L,
                    0L,
                    null,
                    false)
                );

        this.mockMvc.perform(put("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "urlId": "google",
                            "newTimer": "30"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").isNotEmpty())
                .andExpect(jsonPath("$.shortUrl").value("google"))
                .andExpect(jsonPath("$.deleteAfterDays").isNotEmpty())
                .andExpect(jsonPath("$.deleteAfterDays").value("30"))
                .andExpect(jsonPath("$.longUrl").isNotEmpty())
                .andExpect(jsonPath("$.longUrl").value("https://www.google.com"))
                .andExpect(jsonPath("$..createdAt").isNotEmpty())
        ;
    }

    @Test
    void updateUrlDeleteTimerNull() throws Exception {
        when(urlServiceMock.updateCleanTimer(any(UrlRequestUpdateDeleteTimerDto.class)))
                .thenReturn(
                        null
                );

        this.mockMvc.perform(put("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "urlId": "google",
                            "newTimer": "30"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void updateUrlDeleteTimerNullUrlId() throws Exception {
        when(urlServiceMock.updateCleanTimer(any(UrlRequestUpdateDeleteTimerDto.class)))
                .thenReturn(new UrlCopyEntityDto(
                        null,
                        "google",
                        "https://www.google.com",
                        Timestamp.valueOf(LocalDateTime.now()),
                        null,
                        0L,
                        30L,
                        0L,
                        null,
                        false)
                );

        this.mockMvc.perform(put("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "urlId": "google",
                            "newTimer": "30"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void updateUrlDeleteTimerNullDeleteAfterDays() throws Exception {
        when(urlServiceMock.updateCleanTimer(any(UrlRequestUpdateDeleteTimerDto.class)))
                .thenReturn(new UrlCopyEntityDto(
                        1L,
                        "google",
                        "https://www.google.com",
                        Timestamp.valueOf(LocalDateTime.now()),
                        null,
                        0L,
                        null,
                        0L,
                        null,
                        false)
                );

        this.mockMvc.perform(put("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "urlId": "google",
                            "newTimer": "30"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void updateUrlDeleteTimerWrong() throws Exception {
        when(urlServiceMock.updateCleanTimer(any(UrlRequestUpdateDeleteTimerDto.class)))
                .thenReturn(new UrlCopyEntityDto(
                        1L,
                        "google",
                        "https://www.google.com",
                        Timestamp.valueOf(LocalDateTime.now()),
                        null,
                        0L,
                        7L,
                        0L,
                        null,
                        false)
                );

        this.mockMvc.perform(put("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "urlId": "google",
                            "newTimer": "30"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isNotModified())
                .andExpect(jsonPath("$.shortUrl").isNotEmpty())
                .andExpect(jsonPath("$.shortUrl").value("google"))
                .andExpect(jsonPath("$.deleteAfterDays").isNotEmpty())
                .andExpect(jsonPath("$.deleteAfterDays").value("7"))
                .andExpect(jsonPath("$.longUrl").isNotEmpty())
                .andExpect(jsonPath("$.longUrl").value("https://www.google.com"))
                .andExpect(jsonPath("$..createdAt").isNotEmpty())
        ;
    }
}
