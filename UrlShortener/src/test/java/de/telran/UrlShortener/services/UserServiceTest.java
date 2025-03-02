package de.telran.UrlShortener.services;

import de.telran.UrlShortener.dtos.UserCopyEntityDto;
import de.telran.UrlShortener.entities.UserEntity;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import de.telran.UrlShortener.repositories.UserRepository;
import de.telran.UrlShortener.utils.mapper.Mappers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @InjectMocks
    private UserService userServiceTest;

//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }

    @Test
    void createUser() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }





    @Test
    void getAllUsers() {
        UserEntity userEntity1 = new UserEntity(
                1L,
                "client@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345"
        );

        UserEntity userEntity2 = new UserEntity(
                2L,
                "admin@example.com",
                UserRoleEnum.ADMIN,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "54321"
        );

        UserCopyEntityDto userDto1 = new UserCopyEntityDto(
                1L,
                "client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "1***5"
        );

        UserCopyEntityDto userDto2 = new UserCopyEntityDto(
                2L,
                "admin2@example.com",
                UserRoleEnum.ADMIN,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "5***1"
        );


        when(userRepositoryMock.findAll()).thenReturn(List.of(userEntity1, userEntity2));
        when(mappersMock.convertToUserCopyDto(userEntity1)).thenReturn(userDto1);
        when(mappersMock.convertToUserCopyDto(userEntity2)).thenReturn(userDto2);

        List<UserCopyEntityDto> actualUsers = userServiceTest.getAllUsers();

        assertNotNull(actualUsers);
        assertTrue(actualUsers.size() > 0);
        assertEquals(2, actualUsers.size());
        assertEquals(1, actualUsers.get(0).getUserId());
        assertEquals(UserRoleEnum.CLIENT, actualUsers.get(0).getRole());
        verify(userRepositoryMock).findAll();
        verify(userRepositoryMock, times(1)).findAll();
        //verify(mappersMock).convertToUserCopyDto(any(UserEntity.class));
        verify(mappersMock, times(2)).convertToUserCopyDto(any(UserEntity.class));


    }

}

/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long userId;

    @Column(name = "Email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    private UserRoleEnum role;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private UserStatusEnum status;

    @Column(name = "RegisteredAt")
    private Timestamp registeredAt;

    @Column(name = "LastActiveAt")
    private Timestamp lastActiveAt;

    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

    @Column(name = "PasswordHash")
    private String passwordHash;


 */