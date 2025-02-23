package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.UserEntity;
import de.telran.UrlShortener.mapper.Mappers;
import de.telran.UrlShortener.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mappers mappers;

    private List<UserDto> userList;

    public void createUser(UserRequestDto user){

    }

    public UserResponseDto getUserById(Long userId){
        UserResponseDto retUser = new UserResponseDto();
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity != null) {
            retUser = mappers.convertToUserResponseDto(userEntity);
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

    public UserResponseDto getUser(UserRequestDto user){
        UserResponseDto retUser = new UserResponseDto();
        UserEntity userEntity = userRepository.findUserByEmail(user.getEmail());
        if (userEntity != null) {
            retUser = mappers.convertToUserResponseDto(userEntity);
        }

        return retUser;
    }

    public UserCopyEntityDto updateUser(UserRequestUpdateStatusDto updateUser) {

        UserEntity user = userRepository.findUserByEmail(updateUser.getEmail());
        UserCopyEntityDto retUser = new UserCopyEntityDto();

        if (user != null && user.getEmail() == updateUser.getEmail()){
            user.setStatus(updateUser.getStatus());
            user = userRepository.save(user);

            if(user != null && user.getEmail() == updateUser.getEmail()) {
                retUser = mappers.convertToUserCopyDto(user);
            }
        }
        return retUser;
    }

    public boolean deleteUser(UserRequestDto delUser){
        UserEntity user = userRepository.findUserByEmail(delUser.getEmail());
        boolean ret = true;

        if (user != null && user.getEmail() == delUser.getEmail()){
            userRepository.delete(user);

            user = userRepository.findUserByEmail(delUser.getEmail());
            if (user != null && user.getEmail() == delUser.getEmail()){
                ret = false;
            }
        }
        return ret;
    }

    public boolean deleteUser(String email){
        UserEntity user = userRepository.findUserByEmail(email);
        boolean ret = true;

        if (user != null && user.getEmail() == email){
            userRepository.delete(user);

            user = userRepository.findUserByEmail(email);
            if (user != null && user.getEmail() == email){
                ret = false;
            }
        }
        return ret;
    }

    public boolean deleteUser(Long userId){
        UserEntity user = userRepository.findById(userId).orElse(null);
        boolean ret = true;

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
        List<UserEntity> users = userRepository.findAll();
        List<UserCopyEntityDto> usersCopy = MapperUtil.convertList(users, mappers::convertToUserCopyDto);
        return usersCopy;
    }

    public List<UserCopyEntityDto> getAllUsers1(){
        List<UserEntity> users = userRepository.findAll();
        List<UserCopyEntityDto> usersCopy = MapperUtil.convertList(users, mappers::convertToUserCopyDto);
        return usersCopy;
    }

}
