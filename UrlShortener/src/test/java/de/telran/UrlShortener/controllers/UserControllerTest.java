package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.UserCopyEntityDto;
import de.telran.UrlShortener.dtos.UserDto;
import de.telran.UrlShortener.dtos.UserResponseDto;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
//import de.telran.UrlShortener.services.StatisticService;
import de.telran.UrlShortener.services.UserService;
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
import static org.mockito.Mockito.when;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
////import de.telran.onlineshop.dto.CategoryDto;
////import de.telran.onlineshop.security.configure.SecurityConfig;
////import de.telran.onlineshop.security.jwt.JwtAuthentication;
////import de.telran.onlineshop.security.jwt.JwtProvider;
////import de.telran.onlineshop.service.CategoriesService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
////import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.List;

//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
////import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
////import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServiceMock;




    /*

    @Test
    void createUser() {
    }
*/
    @Test
    void getAllUsers() throws Exception {
        when(userServiceMock.getAllUsers()).thenReturn(List.of(new UserCopyEntityDto(
                1L,
                "test@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345")));

        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..userId").exists())
                .andExpect(jsonPath("$..userId").value(1))
                .andExpect(jsonPath("$..email").value("test@example.com"))
                .andExpect(jsonPath("$..role").value("CLIENT"))
                .andExpect(jsonPath("$..status").value("ACTIVE"))
                .andExpect(jsonPath("$..registeredAt").isNotEmpty())
                .andExpect(jsonPath("$..passwordHash").isNotEmpty())
        ;



    }
/*
UserResponseDto
    private Long userId;
    private String email;
    private UserRoleEnum role;
    private UserStatusEnum status;
    private Timestamp registeredAt;
    private Timestamp lastActiveAt; // ? should be taken as last generated short link
    private Timestamp updatedAt;
    private String passwordHash;

 */


    @Test
    void getUserByEmail() throws Exception {
        String testEmail = "test@example.com";
        UserDto userDto = new UserDto(testEmail);

        when(userServiceMock.getUserByEmail(testEmail)).thenReturn(new UserResponseDto(
                        1L,
                        "test@example.com",
                        UserRoleEnum.CLIENT,
                        UserStatusEnum.ACTIVE,
                        Timestamp.valueOf(LocalDateTime.now()),
                        null,
                        Timestamp.valueOf(LocalDateTime.now()),
                        "12345")
        );


        this.mockMvc.perform(get("/users/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "test@example.com"
                        }
                        """
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..userId").exists())
                .andExpect(jsonPath("$..userId").value(1))
                .andExpect(jsonPath("$..email").value("test@example.com"))
                .andExpect(jsonPath("$..role").value("CLIENT"))
                .andExpect(jsonPath("$..status").value("ACTIVE"))
                .andExpect(jsonPath("$..registeredAt").isNotEmpty())
                .andExpect(jsonPath("$..passwordHash").isNotEmpty())
        ;



    }
/*
    @Test
    void updateUserStatus() {
    }

    @Test
    void deleteUser() {
    }
*/

}