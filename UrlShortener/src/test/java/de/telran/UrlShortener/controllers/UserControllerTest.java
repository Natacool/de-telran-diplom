package de.telran.UrlShortener.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
//import de.telran.UrlShortener.services.StatisticService;
import de.telran.UrlShortener.services.UserService;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServiceMock;

    ////////////////////////////////////////////////////////////////////
    // CREATE USER
    //
    //
    @Test
    void createUser() throws Exception {
        when(userServiceMock.createUser(any(UserRequestDto.class))).thenReturn(new UserResponseDto(
                1L,
                "test@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345")
        );

        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "status": "BLOCKED"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$..userId").exists())
                .andExpect(jsonPath("$..userId").isNotEmpty())
                .andExpect(jsonPath("$..userId").value(1))
                .andExpect(jsonPath("$..email").value("test@example.com"))
                .andExpect(jsonPath("$..email").isNotEmpty())
                .andExpect(jsonPath("$..role").value("CLIENT"))
                .andExpect(jsonPath("$..status").value("ACTIVE"))
                .andExpect(jsonPath("$..registeredAt").isNotEmpty())
                .andExpect(jsonPath("$..passwordHash").isNotEmpty())
        ;
    }

    // user = null --> HttpStatus.BAD_REQUEST
    @Test
    void createUserNull() throws Exception {
        when(userServiceMock.createUser(any(UserRequestDto.class))).thenReturn(null);

        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "password": "123456"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    // user.getUserId() = null --> HttpStatus.BAD_REQUEST
    @Test
    void createUserNullUserId() throws Exception {
        when(userServiceMock.createUser(any(UserRequestDto.class))).thenReturn(new UserResponseDto(
                null,
                "test@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345")
        );

        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "password": "12345"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    // user.getPasswordHash() = null --> HttpStatus.BAD_REQUEST
    @Test
    void createUserNullHash() throws Exception {
        when(userServiceMock.createUser(any(UserRequestDto.class))).thenReturn(new UserResponseDto(
                1L,
                "test@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                null)
        );

        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "password": "12345"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    // user.getEmail() = null --> HttpStatus.BAD_REQUEST
    @Test
    void createUserNullEmail() throws Exception {
        String testEmail = "test@example.com";

        when(userServiceMock.createUser(any(UserRequestDto.class))).thenReturn(new UserResponseDto(
                1L,
                null,
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345")
        );

        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "password": "12345"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$..userId").isNotEmpty())
//                .andExpect(jsonPath("$..userId").value(1))
        ;
    }

    // user.getEmail() != newUser.getEmail() --> HttpStatus.BAD_REQUEST
    @Test
    void createUserEmailWrong() throws Exception {
        String testEmail = "wrong@example.com";

        when(userServiceMock.createUser(any(UserRequestDto.class))).thenReturn(new UserResponseDto(
                1L,
                testEmail,
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345")
        );

        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "status": "BLOCKED"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..email").isNotEmpty())
                .andExpect(jsonPath("$..email").value(testEmail))
        ;

    }

    //  user.getPasswordHash() = ""  -->  status = HttpStatus.CONFLICT;
    @Test
    void createUserEmptyHash() throws Exception {
        when(userServiceMock.createUser(any(UserRequestDto.class))).thenReturn(new UserResponseDto(
                1L,
                "test@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "")
        );

        this.mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "status": "BLOCKED"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$..passwordHash").isNotEmpty())
        ;
    }



    ////////////////////////////////////////////////////////////////////
    // GET ALL USERs
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

    ////////////////////////////////////////////////////////////////////
    // GET USER BY EMAIL
    // getUserByEmail UserResponseDto user != null && user.getUserId() != null
    @Test
    void getUserByEmail() throws Exception {
        String testEmail = "test@example.com";
        //UserDto userDto = new UserDto(testEmail);

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
                .andExpect(jsonPath("$..userId").isNotEmpty())
                .andExpect(jsonPath("$..userId").value(1))
                .andExpect(jsonPath("$..email").value("test@example.com"))
                .andExpect(jsonPath("$..email").isNotEmpty())
                .andExpect(jsonPath("$..role").value("CLIENT"))
                .andExpect(jsonPath("$..status").value("ACTIVE"))
                .andExpect(jsonPath("$..registeredAt").isNotEmpty())
                .andExpect(jsonPath("$..passwordHash").isNotEmpty())
        ;
    }

    // getUserByEmail UserResponseDto user null
    @Test
    void getUserByEmailUserNull() throws Exception {
        String testEmail = "test@example.com";
        when(userServiceMock.getUserByEmail(testEmail)).thenReturn(null);

        this.mockMvc.perform(get("/users/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    // getUserByEmail UserResponseDto user != null && user.getUserId() == null
    @Test
    void getUserByEmailUserIdNull() throws Exception {
        String testEmail = "test@example.com";
        //UserDto userDto = new UserDto(testEmail);
        //Optional.ofNullable(null)
        when(userServiceMock.getUserByEmail(testEmail)).thenReturn(new UserResponseDto(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)
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
                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$..userId").isEmpty())
        ;
    }

    ////////////////////////////////////////////////////////////////////
    // UPDATE USER
    // UserResponseDto != null & status = BLOCKED -> OK
    @Test
    void updateUserStatus() throws Exception {
        String testEmail = "test@example.com";

//        UserRequestUpdateDto updateUser1 = new UserRequestUpdateDto();
//        UserRequestUpdateDto updateUser = new UserRequestUpdateDto(
//                "test@example.com",
//                UserStatusEnum.BLOCKED
//        );

        //when(userServiceMock.updateUser(updateUser)).thenReturn(new UserResponseDto(
        when(userServiceMock.updateUser(any(UserRequestUpdateDto.class))).thenReturn(new UserResponseDto(
                1L,
                "test@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.BLOCKED,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345")
        );

        this.mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "status": "BLOCKED"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..userId").exists())
                .andExpect(jsonPath("$..userId").isNotEmpty())
                .andExpect(jsonPath("$..userId").value(1))
                .andExpect(jsonPath("$..email").value("test@example.com"))
                .andExpect(jsonPath("$..email").isNotEmpty())
//                .andExpect(jsonPath("$..role").value("CLIENT"))
                .andExpect(jsonPath("$..status").value("BLOCKED"))
//                .andExpect(jsonPath("$..registeredAt").isNotEmpty())
//                .andExpect(jsonPath("$..passwordHash").isNotEmpty())
        ;
    }

    // UserResponseDto != null & status = Active -> fail
    @Test
    void updateUserStatusNotUpdated() throws Exception {
        String testEmail = "test@example.com";

//        UserRequestUpdateDto updateUser1 = new UserRequestUpdateDto();
//        UserRequestUpdateDto updateUser = new UserRequestUpdateDto(
//                "test@example.com",
//                UserStatusEnum.BLOCKED
//        );

        //when(userServiceMock.updateUser(updateUser)).thenReturn(new UserResponseDto(
        when(userServiceMock.updateUser(any(UserRequestUpdateDto.class))).thenReturn(new UserResponseDto(
                1L,
                "test@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345")
        );

        this.mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "status": "BLOCKED"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isNotModified())
                .andExpect(jsonPath("$..userId").exists())
                .andExpect(jsonPath("$..userId").isNotEmpty())
                .andExpect(jsonPath("$..userId").value(1))
                .andExpect(jsonPath("$..email").value("test@example.com"))
                .andExpect(jsonPath("$..email").isNotEmpty())
                .andExpect(jsonPath("$..status").isNotEmpty())
//                .andExpect(jsonPath("$..role").value("CLIENT"))
                .andExpect(jsonPath("$..status").value("ACTIVE"))
//                .andExpect(jsonPath("$..registeredAt").isNotEmpty())
//                .andExpect(jsonPath("$..passwordHash").isNotEmpty())
        ;
    }

    // UserResponseDto user = null
    @Test
    void updateUserStatusNull() throws Exception {
        String testEmail = "test@example.com";

        when(userServiceMock.updateUser(any(UserRequestUpdateDto.class))).thenReturn(
                null
        );

        this.mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "status": "BLOCKED"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }


    // user.getUserId() == null
    @Test
    void updateUserStatusNullUserId() throws Exception {
        when(userServiceMock.updateUser(any(UserRequestUpdateDto.class))).thenReturn(new UserResponseDto(
                null,
                "test@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345")
        );

        this.mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "status": "BLOCKED"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$..userId").isNotEmpty())
//                .andExpect(jsonPath("$..userId").value(1))
        ;
    }

    // user.getEmail() == null
    @Test
    void updateUserStatusNullEmail() throws Exception {
        String testEmail = "test@example.com";

        when(userServiceMock.updateUser(any(UserRequestUpdateDto.class))).thenReturn(new UserResponseDto(
                1L,
                null,
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345")
        );

        this.mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "status": "BLOCKED"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$..userId").isNotEmpty())
//                .andExpect(jsonPath("$..userId").value(1))
        ;
    }

    // user.getEmail() != request email
    @Test
    void updateUserStatusEmailWrong() throws Exception {
        String testEmail = "wrong@example.com";

        when(userServiceMock.updateUser(any(UserRequestUpdateDto.class))).thenReturn(new UserResponseDto(
                null,
                testEmail,
                null,
                null,
                null,
                null,
                null,
                null)
        );

        this.mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "status": "BLOCKED"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..email").isNotEmpty())
                .andExpect(jsonPath("$..email").value(testEmail))
        ;
    }



    // user.getStatus() == null
    @Test
    void updateUserStatusNullStatus() throws Exception {
        String testEmail = "test@example.com";

        when(userServiceMock.updateUser(any(UserRequestUpdateDto.class))).thenReturn(new UserResponseDto(
                1L,
                "test@example.com",
                UserRoleEnum.CLIENT,
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345")
        );

        this.mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com",
                            "status": "BLOCKED"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$..userId").isNotEmpty())
//                .andExpect(jsonPath("$..userId").value(1))
        ;
    }

    ////////////////////////////////////////////////////////////////////
    // DELETE USER
    @Test
    void deleteUserTrue() throws Exception {
        String testEmail = "test@example.com";
        when(userServiceMock.deleteUser(testEmail)).thenReturn(true);

        this.mockMvc.perform(delete("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "test@example.com"
                        }
                        """
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("USER: test@example.com deleted"))
        ;
    }

    @Test
    void deleteUserFalse() throws Exception {
        String testEmail = "test@example.com";
        when(userServiceMock.deleteUser(testEmail)).thenReturn(false);

        this.mockMvc.perform(delete("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "test@example.com"
                        }
                        """
                        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: USER: test@example.com NOT deleted"))
        ;
    }
}