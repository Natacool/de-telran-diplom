package de.telran.UrlShortener.services;

import de.telran.UrlShortener.dtos.UserCopyEntityDto;
import de.telran.UrlShortener.dtos.UserRequestDto;
import de.telran.UrlShortener.dtos.UserRequestUpdateDto;
import de.telran.UrlShortener.dtos.UserResponseDto;
import de.telran.UrlShortener.entities.UserEntity;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
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

    @Mock
    private CommonUtils utilsMock;

    private UserEntity userEntity1;
    private UserEntity userEntity2;
    private UserCopyEntityDto userCopyEntityDto1;
    private UserCopyEntityDto userCopyEntityDto2;
    private UserRequestDto userRequestDto1;
    private UserRequestUpdateDto userRequestUpdateDto1;
    private UserResponseDto userResponseDto1;
    private String testEmail;
    private Timestamp now;

    @BeforeEach
    void setUp() {
        testEmail = "test@example.com";

        now = Timestamp.valueOf(LocalDateTime.now());

        userEntity1 = new UserEntity(
                1L,
                "client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345"
        );

        userEntity2 = new UserEntity(
                2L,
                "admin2@example.com",
                UserRoleEnum.ADMIN,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "54321"
        );

        userCopyEntityDto1 = new UserCopyEntityDto(
                1L,
                "client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "1***5"
        );

        userCopyEntityDto2 = new UserCopyEntityDto(
                2L,
                "admin2@example.com",
                UserRoleEnum.ADMIN,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "5***1"
        );

        userRequestDto1 = new UserRequestDto(
                "client1@example.com",
                "12345"
        );

        userRequestUpdateDto1 = new UserRequestUpdateDto(
                "client1@example.com",
                UserStatusEnum.BLOCKED
        );
        userResponseDto1 = new UserResponseDto(
                1L,
                "client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "****5"
        );
    }

    // findUserByEmail() --> !NULL
    @Test
    void createUser() {
        UserResponseDto expected = new UserResponseDto(
                1L,
                "client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "****5"
        );

        when(userRepositoryMock.findUserByEmail(any(String.class))).thenReturn(userEntity1);
        when(mappersMock.convertToUserResponseDto(any(UserEntity.class))).thenReturn(userResponseDto1);

        UserResponseDto actualUser = userServiceTest.createUser(userRequestDto1);
        verify(userRepositoryMock, times(1)).findUserByEmail(any(String.class));
        verify(mappersMock, times(1)).convertToUserResponseDto(any(UserEntity.class));
        verify(userRepositoryMock, times(0)).save(any(UserEntity.class));

        assertNotNull(actualUser);
        assertNotNull(actualUser.getUserId());
        assertNotNull(actualUser.getEmail());
        assertNotNull(actualUser.getRegisteredAt());
        assertNotNull(actualUser.getUpdatedAt());
        assertEquals(expected.getUserId(), actualUser.getUserId());
        assertEquals(expected.getEmail(), actualUser.getEmail());
        assertEquals(expected.getRole(), actualUser.getRole());
        assertEquals(expected.getStatus(), actualUser.getStatus());
        assertNotEquals(expected.getPasswordHash(), actualUser.getPasswordHash());
        assertEquals("", actualUser.getPasswordHash());
    }

    // findUserByEmail() --> NULL
    // save() --> !NULL
    @Test
    void createUserNullFind() {
        UserResponseDto expected = new UserResponseDto(
                1L,
                "client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "****5"
        );

        when(userRepositoryMock.findUserByEmail(any(String.class))).thenReturn(null);
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userEntity1);
        when(mappersMock.convertToUserResponseDto(any(UserEntity.class))).thenReturn(userResponseDto1);
        when(utilsMock.getCurrentTimestamp()).thenReturn(now);

        UserResponseDto actualUser = userServiceTest.createUser(userRequestDto1);
        verify(userRepositoryMock, times(1)).findUserByEmail(any(String.class));
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
        verify(mappersMock, times(1)).convertToUserResponseDto(any(UserEntity.class));
        verify(utilsMock, times(1)).getCurrentTimestamp();

        assertNotNull(actualUser);
        assertNotNull(actualUser.getUserId());
        assertNotNull(actualUser.getEmail());
        assertNotNull(actualUser.getRegisteredAt());
        assertNotNull(actualUser.getUpdatedAt());
        assertEquals(expected.getUserId(), actualUser.getUserId());
        assertEquals(expected.getEmail(), actualUser.getEmail());
        assertEquals(expected.getRole(), actualUser.getRole());
        assertEquals(expected.getStatus(), actualUser.getStatus());
        assertNotEquals("", actualUser.getPasswordHash());
        assertEquals(expected.getPasswordHash(), actualUser.getPasswordHash());
    }

    // findUserByEmail() --> NULL
    // save() --> NULL
    @Test
    void createUserNullFindNullSave() {
        when(userRepositoryMock.findUserByEmail(any(String.class))).thenReturn(null);
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(null);
        when(utilsMock.getCurrentTimestamp()).thenReturn(now);

        UserResponseDto actualUser = userServiceTest.createUser(userRequestDto1);
        verify(userRepositoryMock, times(1)).findUserByEmail(any(String.class));
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
        verify(mappersMock, times(0)).convertToUserResponseDto(any(UserEntity.class));
        verify(utilsMock, times(1)).getCurrentTimestamp();

        assertNotNull(actualUser);
        assertNull(actualUser.getUserId());
        assertNull(actualUser.getEmail());
        assertNull(actualUser.getPasswordHash());
    }

    // userRepository.findUserByEmail() -->  !NULL
    @Test
    void getUserByEmail() {
        when(userRepositoryMock.findUserByEmail(userEntity1.getEmail())).thenReturn(userEntity1);
        when(mappersMock.convertToUserResponseDto(userEntity1)).thenReturn(userResponseDto1);

        UserResponseDto actualUser = userServiceTest.getUserByEmail(userEntity1.getEmail());
        assertNotNull(actualUser);
        verify(userRepositoryMock, times(1)).findUserByEmail(any(String.class));
        verify(mappersMock, times(1)).convertToUserResponseDto(any(UserEntity.class));
        assertNotNull(actualUser.getUserId());
        assertNotNull(actualUser.getEmail());
        assertEquals(userResponseDto1.getUserId(), actualUser.getUserId());
        assertEquals(userResponseDto1.getEmail(), actualUser.getEmail());
        assertEquals(userResponseDto1.getRole(), actualUser.getRole());
        assertEquals(userResponseDto1.getStatus(), actualUser.getStatus());
        assertEquals(userResponseDto1.getRegisteredAt(), actualUser.getRegisteredAt());
        assertEquals(userResponseDto1.getUpdatedAt(), actualUser.getUpdatedAt());
        assertEquals(userResponseDto1.getPasswordHash(), actualUser.getPasswordHash());
    }

    // userRepository.findUserByEmail() -->  NULL
    @Test
    void getUserByEmailNull() {
        when(userRepositoryMock.findUserByEmail(any(String.class))).thenReturn(null);

        UserResponseDto actualUser = userServiceTest.getUserByEmail(testEmail);
        assertNotNull(actualUser);
        verify(userRepositoryMock, times(1)).findUserByEmail(any(String.class));
        assertNull(actualUser.getUserId());
        assertNull(actualUser.getEmail());
    }

    // findUserByEmail --> !NULL
    // user.getEmail() = updateUser.getEmail()
    // save --> !NULL
    // AFTER save user != null
    // AFTER save user.getEmail() = updateUser.getEmail()
    // AFTER save user.getStatus() = updateUser.getStatus()
    // mappers.convertToUserResponseDto
    @Test
    void updateUser() {
        UserResponseDto expectedDto = new UserResponseDto(
                1L,
                "client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.BLOCKED,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "****5"
        );

        UserEntity expectedEntity = new UserEntity(
                1L,
                "client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.BLOCKED,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345"
        );


        when(userRepositoryMock.findUserByEmail(any(String.class))).thenReturn(userEntity1);
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(expectedEntity);
        when(mappersMock.convertToUserResponseDto(any(UserEntity.class))).thenReturn(expectedDto);
        when(utilsMock.getCurrentTimestamp()).thenReturn(now);

        UserResponseDto actualUser = userServiceTest.updateUser(userRequestUpdateDto1);
        verify(userRepositoryMock, times(1)).findUserByEmail(any(String.class));
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
        verify(mappersMock, times(1)).convertToUserResponseDto(any(UserEntity.class));
        verify(utilsMock, times(1)).getCurrentTimestamp();

        assertNotNull(actualUser);
        assertNotNull(actualUser.getUserId());
        assertNotNull(actualUser.getEmail());
        assertNotNull(actualUser.getRole());
        assertNotNull(actualUser.getStatus());
        assertNotNull(actualUser.getRegisteredAt());
        assertNotNull(actualUser.getUpdatedAt());
        assertEquals(expectedDto.getUserId(), actualUser.getUserId());
        assertEquals(expectedDto.getEmail(), actualUser.getEmail());
        assertEquals(expectedDto.getRole(), actualUser.getRole());
        assertEquals(expectedDto.getStatus(), actualUser.getStatus());
        assertEquals(expectedDto.getPasswordHash(), actualUser.getPasswordHash());
        assertNotEquals("", actualUser.getPasswordHash());
    }

    // findUserByEmail --> NULL
    // no mapper
    @Test
    void updateUserNullFind() {
        when(userRepositoryMock.findUserByEmail(any(String.class))).thenReturn(null);

        UserResponseDto actualUser = userServiceTest.updateUser(userRequestUpdateDto1);
        verify(userRepositoryMock, times(1)).findUserByEmail(any(String.class));
        verify(userRepositoryMock, times(0)).save(any(UserEntity.class));
        verify(mappersMock, times(0)).convertToUserResponseDto(any(UserEntity.class));

        assertNotNull(actualUser);
        assertNull(actualUser.getUserId());
        assertNull(actualUser.getEmail());
        assertNull(actualUser.getRole());
        assertNull(actualUser.getStatus());
        assertNull(actualUser.getRegisteredAt());
        assertNull(actualUser.getUpdatedAt());
    }

    // findUserByEmail --> !NULL
    // user.getEmail() != updateUser.getEmail()
    // no mapper
    @Test
    void updateUserFindEmailWrong() {
        UserEntity expectedEntity = new UserEntity(
                1L,
                "not_client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.BLOCKED,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345"
        );

        when(userRepositoryMock.findUserByEmail(any(String.class))).thenReturn(expectedEntity);

        UserResponseDto actualUser = userServiceTest.updateUser(userRequestUpdateDto1);
        verify(userRepositoryMock, times(1)).findUserByEmail(any(String.class));
        verify(userRepositoryMock, times(0)).save(any(UserEntity.class));
        verify(mappersMock, times(0)).convertToUserResponseDto(any(UserEntity.class));

        assertNotNull(actualUser);
        assertNull(actualUser.getUserId());
        assertNull(actualUser.getEmail());
        assertNull(actualUser.getRole());
        assertNull(actualUser.getStatus());
        assertNull(actualUser.getRegisteredAt());
        assertNull(actualUser.getUpdatedAt());
    }

    // findUserByEmail --> !NULL
    // user.getEmail() = updateUser.getEmail()
    // save --> NULL, AFTER save user = null
    // no mapper
    @Test
    void updateUserNullSave() {
        when(userRepositoryMock.findUserByEmail(any(String.class))).thenReturn(userEntity1);
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(null);
        when(utilsMock.getCurrentTimestamp()).thenReturn(now);

        UserResponseDto actualUser = userServiceTest.updateUser(userRequestUpdateDto1);
        verify(userRepositoryMock, times(1)).findUserByEmail(any(String.class));
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
        verify(mappersMock, times(0)).convertToUserResponseDto(any(UserEntity.class));
        verify(utilsMock, times(1)).getCurrentTimestamp();

        assertNotNull(actualUser);
        assertNull(actualUser.getUserId());
        assertNull(actualUser.getEmail());
        assertNull(actualUser.getRole());
        assertNull(actualUser.getStatus());
        assertNull(actualUser.getRegisteredAt());
        assertNull(actualUser.getUpdatedAt());
    }

    // findUserByEmail --> !NULL
    // user.getEmail() = updateUser.getEmail()
    // save --> !NULL, AFTER save user != null
    // AFTER save user.getEmail() != updateUser.getEmail()
    // no mapper
    @Test
    void updateUserSaveEmailWrong() {
        UserEntity expectedEntity = new UserEntity(
                1L,
                "not_client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.BLOCKED,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345"
        );

        when(userRepositoryMock.findUserByEmail(any(String.class))).thenReturn(userEntity1);
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(expectedEntity);
        when(utilsMock.getCurrentTimestamp()).thenReturn(now);

        UserResponseDto actualUser = userServiceTest.updateUser(userRequestUpdateDto1);
        verify(userRepositoryMock, times(1)).findUserByEmail(any(String.class));
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
        verify(mappersMock, times(0)).convertToUserResponseDto(any(UserEntity.class));
        verify(utilsMock, times(1)).getCurrentTimestamp();

        assertNotNull(actualUser);
        assertNull(actualUser.getUserId());
        assertNull(actualUser.getEmail());
        assertNull(actualUser.getRole());
        assertNull(actualUser.getStatus());
        assertNull(actualUser.getRegisteredAt());
        assertNull(actualUser.getUpdatedAt());
    }

    // findUserByEmail --> !NULL
    // user.getEmail() = updateUser.getEmail()
    // save --> !NULL, AFTER save user != null
    // AFTER save user != null
    // AFTER save user.getEmail() = updateUser.getEmail()
    // AFTER save user.getStatus() != updateUser.getStatus()
    // no mapper
    @Test
    void updateUserSaveStatusWrong() {
        UserEntity expectedEntity = new UserEntity(
                1L,
                "client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345"
        );

        when(userRepositoryMock.findUserByEmail(any(String.class))).thenReturn(userEntity1);
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(expectedEntity);
        when(utilsMock.getCurrentTimestamp()).thenReturn(now);

        UserResponseDto actualUser = userServiceTest.updateUser(userRequestUpdateDto1);
        verify(userRepositoryMock, times(1)).findUserByEmail(any(String.class));
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
        verify(mappersMock, times(0)).convertToUserResponseDto(any(UserEntity.class));
        verify(utilsMock, times(1)).getCurrentTimestamp();

        assertNotNull(actualUser);
        assertNull(actualUser.getUserId());
        assertNull(actualUser.getEmail());
        assertNull(actualUser.getRole());
        assertNull(actualUser.getStatus());
        assertNull(actualUser.getRegisteredAt());
        assertNull(actualUser.getUpdatedAt());
    }

    // findUserNotAdminNotDeletedByEmail --> !NULL
    // save --> !NULL
    // user.getStatus() --> !NULL
    // user.getStatus() --> expectedStatus
    @Test
    void deleteUser() {
        UserEntity saved = new UserEntity(
                1L,
                "client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.DELETED,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345"
        );

        when(userRepositoryMock.findUserNotAdminNotDeletedByEmail(any(String.class))).thenReturn(userEntity1);
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(saved);
        when(utilsMock.getCurrentTimestamp()).thenReturn(now);

        boolean res = userServiceTest.deleteUser(userEntity1.getEmail());
        verify(userRepositoryMock, times(1)).findUserNotAdminNotDeletedByEmail(any(String.class));
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
        verify(utilsMock, times(1)).getCurrentTimestamp();

        assertTrue(res);
    }

    // findUserNotAdminNotDeletedByEmail --> NULL
    @Test
    void deleteUserFindReturnNull() {
        when(userRepositoryMock.findUserNotAdminNotDeletedByEmail(any(String.class))).thenReturn(null);

        boolean res = userServiceTest.deleteUser(userEntity1.getEmail());
        verify(userRepositoryMock, times(1)).findUserNotAdminNotDeletedByEmail(any(String.class));
        verify(userRepositoryMock, times(0)).save(any(UserEntity.class));

        assertFalse(res);
    }

    // findUserNotAdminNotDeletedByEmail --> !NULL
    // save --> NULL
    @Test
    void deleteUserSaveReturnNull() {
        when(userRepositoryMock.findUserNotAdminNotDeletedByEmail(any(String.class))).thenReturn(userEntity1);
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(null);
        when(utilsMock.getCurrentTimestamp()).thenReturn(now);

        boolean res = userServiceTest.deleteUser(userEntity1.getEmail());
        verify(userRepositoryMock, times(1)).findUserNotAdminNotDeletedByEmail(any(String.class));
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
        verify(utilsMock, times(1)).getCurrentTimestamp();

        assertFalse(res);
    }

    // findUserNotAdminNotDeletedByEmail --> !NULL
    // save --> !NULL
    // user.getStatus() --> NULL
    @Test
    void deleteUserStatusNull() {
        UserEntity saved = new UserEntity(
                1L,
                "client1@example.com",
                UserRoleEnum.CLIENT,
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345"
        );

        when(userRepositoryMock.findUserNotAdminNotDeletedByEmail(any(String.class))).thenReturn(userEntity1);
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(saved);
        when(utilsMock.getCurrentTimestamp()).thenReturn(now);


        boolean res = userServiceTest.deleteUser(userEntity1.getEmail());
        verify(userRepositoryMock, times(1)).findUserNotAdminNotDeletedByEmail(any(String.class));
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
        verify(utilsMock, times(1)).getCurrentTimestamp();

        assertFalse(res);
    }

    // findUserNotAdminNotDeletedByEmail --> !NULL
    // save --> !NULL
    // user.getStatus() --> !NULL
    // user.getStatus() --> !expectedStatus
    @Test
    void deleteUserStatusWrong() {
        UserEntity saved = new UserEntity(
                1L,
                "client1@example.com",
                UserRoleEnum.CLIENT,
                UserStatusEnum.ACTIVE,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                "12345"
        );

        when(userRepositoryMock.findUserNotAdminNotDeletedByEmail(any(String.class))).thenReturn(userEntity1);
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(saved);
        when(utilsMock.getCurrentTimestamp()).thenReturn(now);

        boolean res = userServiceTest.deleteUser(userEntity1.getEmail());
        verify(userRepositoryMock, times(1)).findUserNotAdminNotDeletedByEmail(any(String.class));
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
        verify(utilsMock, times(1)).getCurrentTimestamp();

        assertFalse(res);
    }

    @Test
    void getAllUsers() {
        when(userRepositoryMock.findAll()).thenReturn(List.of(userEntity1, userEntity2));
        when(mappersMock.convertToUserCopyDto(userEntity1)).thenReturn(userCopyEntityDto1);
        when(mappersMock.convertToUserCopyDto(userEntity2)).thenReturn(userCopyEntityDto2);

        List<UserCopyEntityDto> actualUsers = userServiceTest.getAllUsers();

        assertNotNull(actualUsers);
        assertTrue(actualUsers.size() > 0);
        assertEquals(2, actualUsers.size());
        assertEquals(1, actualUsers.get(0).getUserId());
        assertEquals(UserRoleEnum.CLIENT, actualUsers.get(0).getRole());
        verify(userRepositoryMock).findAll();
        verify(userRepositoryMock, times(1)).findAll();
        verify(mappersMock, times(2)).convertToUserCopyDto(any(UserEntity.class));
    }

    @Test
    void getAllUsersRepoNullList() {
        when(userRepositoryMock.findAll()).thenReturn(null);

        List<UserCopyEntityDto> actualUsers = userServiceTest.getAllUsers();

        assertNotNull(actualUsers);
        assertEquals(0, actualUsers.size());
        verify(userRepositoryMock).findAll();
        verify(userRepositoryMock, times(1)).findAll();
        verify(mappersMock, times(0)).convertToUserCopyDto(any(UserEntity.class));
    }

    @Test
    void getAllUsersRepoEmptyList() {
        when(userRepositoryMock.findAll()).thenReturn(List.of());

        List<UserCopyEntityDto> actualUsers = userServiceTest.getAllUsers();

        assertNotNull(actualUsers);
        assertEquals(0, actualUsers.size());
        verify(userRepositoryMock).findAll();
        verify(userRepositoryMock, times(1)).findAll();
        verify(mappersMock, times(0)).convertToUserCopyDto(any(UserEntity.class));
    }
}
