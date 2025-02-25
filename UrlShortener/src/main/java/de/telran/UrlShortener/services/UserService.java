package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.UserEntity;
import de.telran.UrlShortener.entities.enums.UserRoleEnum;
import de.telran.UrlShortener.entities.enums.UserStatusEnum;
import de.telran.UrlShortener.mapper.Mappers;
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
        if (user == null) {
            user = new UserEntity(null,
                    newUser.getEmail(),
                    UserRoleEnum.CLIENT,
                    UserStatusEnum.ACTIVE,
                    Timestamp.valueOf(LocalDateTime.now()),
                    null,
                    null,
                    newUser.getPassword());
            user = userRepository.save(user);
        }

        UserResponseDto retUser = new UserResponseDto();

        if (user != null) {
            retUser = mappers.convertToUserResponseDto(user);
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

    public UserResponseDto getUser(UserDto user){
        UserResponseDto retUser = new UserResponseDto();
        UserEntity userEntity = userRepository.findUserByEmail(user.getEmail());
        if (userEntity != null) {
            retUser = mappers.convertToUserResponseDto(userEntity);
        }
        return retUser;
    }

    public UserResponseDto getUserById(Long userId){
        UserResponseDto retUser = new UserResponseDto();
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity != null) {
            retUser = mappers.convertToUserResponseDto(userEntity);
        }

        return retUser;
    }
    public UserResponseDto updateUser(UserRequestUpdateStatusDto updateUser) {

        UserEntity user = userRepository.findUserByEmail(updateUser.getEmail());
        UserResponseDto retUser = new UserResponseDto();

        if (user != null && user.getEmail().equals(updateUser.getEmail())){
            user.setStatus(updateUser.getStatus());
            user = userRepository.save(user);

            if(user != null && user.getEmail().equals(updateUser.getEmail())) {
                retUser = mappers.convertToUserResponseDto(user);
            }
        }
        return retUser;
    }

    public Boolean deleteUser(UserRequestDto delUser){
        UserEntity user = userRepository.findUserByEmail(delUser.getEmail());
        Boolean ret = true;

        if (user != null && user.getEmail().equals(delUser.getEmail())){
            userRepository.delete(user);

            user = userRepository.findUserByEmail(delUser.getEmail());
            if (user != null && user.getEmail().equals(delUser.getEmail())){
                ret = false;
            }
        }
        return ret;
    }

    public Boolean deleteUser(String email){
        UserEntity user = userRepository.findUserByEmail(email);
        Boolean ret = true;

        if (user != null && user.getEmail().equals(email)){
            userRepository.delete(user);

            user = userRepository.findUserByEmail(email);
            if (user != null && user.getEmail().equals(email)){
                ret = false;
            }
        } else{
            ret = false;
        }

        return ret;
    }

    public Boolean deleteUser(Long userId){
        UserEntity user = userRepository.findById(userId).orElse(null);
        Boolean ret = true;

        if (user != null){
            userRepository.delete(user);

            user = userRepository.findById(userId).orElse(null);
            if (user != null){
                ret = false;
            }
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

    public List<UserCopyEntityDto> getAllUsers1(){
        List<UserEntity> users = userRepository.findAll();
        List<UserCopyEntityDto> usersCopy = MapperUtil.convertList(users, mappers::convertToUserCopyDto);
        return usersCopy;
    }

}
