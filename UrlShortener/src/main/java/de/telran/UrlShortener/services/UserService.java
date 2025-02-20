package de.telran.UrlShortener.services;

import de.telran.UrlShortener.configure.MapperUtil;
import de.telran.UrlShortener.dtos.UserCopyEntityDto;
import de.telran.UrlShortener.dtos.UserDto;
import de.telran.UrlShortener.dtos.UserRequestDto;
import de.telran.UrlShortener.dtos.UserRequestUpdateStatusDto;
import de.telran.UrlShortener.entities.UserEntity;
import de.telran.UrlShortener.mapper.Mappers;
import de.telran.UrlShortener.repositories.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//@NoArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mappers mappers;

    private List<UserDto> userList;

    // return UserResponseDto
    public void createUser(UserRequestDto user){

    }
    public void updateUser(UserRequestUpdateStatusDto user){

    }
    public void deleteUser(UserRequestDto user){

    }


    public List<UserCopyEntityDto> getAllUsers(){
        List<UserEntity> users = userRepository.findAll();
        List<UserCopyEntityDto> usersCopy = MapperUtil.convertList(users, mappers::convertToUserCopyDto);
        return usersCopy;

    }

    //GET
//???    GetAllUsers - statistic
//???    GetUser(id) - statistic
//???    GetUser(email) - statistic

    //POST
//? Create  User – [user] - admin - or user via login

    //PUT
//??    UpdateStatus(user_id, status) – admin [user]
//    UpdateStatus(user_email, status) – admin [user]
    // input:

    //DELETE
//??    DeleteUser(user_id) – admin [user]
//    DeleteUser(user_email) – admin [user]

}
