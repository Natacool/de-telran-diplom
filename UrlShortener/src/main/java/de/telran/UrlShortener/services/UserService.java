package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.UserEntity;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import de.telran.UrlShortener.utils.mapper.Mappers;
import de.telran.UrlShortener.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mappers mappers;

    public UserResponseDto createUser(UserRequestDto newUser){
        UserEntity user = userRepository.findUserByEmail(newUser.getEmail());
        boolean userExist = false;
        if (user == null) {
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            user = new UserEntity(null,
                    newUser.getEmail(),
                    UserRoleEnum.CLIENT,
                    UserStatusEnum.ACTIVE,
                    now,
                    null,
                    now,
                    newUser.getPassword());
            user = userRepository.save(user);
        } else {
            userExist = true;
        }


        UserResponseDto retUser = new UserResponseDto();
        if (user != null) {
            retUser = mappers.convertToUserResponseDto(user);
            if (userExist) {
                retUser.setPasswordHash("");
            }
        }
        return retUser;
    }

    public UserResponseDto getUserByEmail(String userEmail){
        UserResponseDto retUser = new UserResponseDto();
        UserEntity userEntity = userRepository.findUserByEmail(userEmail);
        if (userEntity != null) {
            retUser = mappers.convertToUserResponseDto(userEntity);
        }
        return retUser;
    }

    public UserResponseDto updateUser(UserRequestUpdateDto updateUser) {

        UserEntity user = userRepository.findUserByEmail(updateUser.getEmail());
        UserResponseDto retUser = new UserResponseDto();

        if (user != null && user.getEmail().equals(updateUser.getEmail())){
            user.setStatus(updateUser.getStatus());
            user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            user = userRepository.save(user);

            if(user != null && user.getEmail().equals(updateUser.getEmail()) &&
                user.getStatus() == updateUser.getStatus()) {
                retUser = mappers.convertToUserResponseDto(user);
            }
        }
        return retUser;
    }
    public Boolean deleteUser(String email){
        UserEntity user = userRepository.findUserNotAdminNotDeletedByEmail(email);
        Boolean ret;

        if (user != null) {
            user.setStatus(UserStatusEnum.DELETED);
            user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            user = userRepository.save(user);

            if (user != null && user.getStatus() != null &&
                    user.getStatus() == UserStatusEnum.DELETED){
                ret = true;
            } else {
                ret = false;
            }
        } else {
            ret = false;
        }
        return ret;
    }

    public List<UserCopyEntityDto> getAllUsers(){
        List<UserCopyEntityDto> usersCopy = new ArrayList<>();
        List<UserEntity> users = userRepository.findAll();
        if (users != null && users.size() > 0){
            usersCopy = MapperUtil.convertList(users, mappers::convertToUserCopyDto);
        }
        return usersCopy;
    }
}
