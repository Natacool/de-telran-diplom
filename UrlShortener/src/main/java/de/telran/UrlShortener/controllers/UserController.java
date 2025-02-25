package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.entities.UserEntity;
import de.telran.UrlShortener.services.UserService;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // User APIs
    //    public void createUser(UserRequestDto user)

    @PostMapping(value = "/create") //Jackson
    public ResponseEntity<Boolean> createUser(@RequestBody UserDto newUser) { //insert
        Boolean res = false;
        //userService.createUser(newUser);
        return ResponseEntity.status(201).body(res);
    }

    @PostMapping //Jackson
    public ResponseEntity<UserResponseDto> insertUser(@RequestBody UserRequestDto newUser) { //insert
        //UserDto user = new UserDto();
        //userService.insertUser(newUser);
        //UserRequestDto user = new UserRequestDto();
        UserResponseDto user = userService.createUser(newUser);
        //UserResponseDto user1 = new UserResponseDto();
        return ResponseEntity.status(201).body(user);
    }

    // Admin APIs
//    public UserResponseDto getUserById(Long userId)
//    public UserResponseDto getUserByEmail(String userEmail)
//    public UserResponseDto getUser(UserRequestDto user)
//    public UserCopyEntityDto updateUser(UserRequestUpdateStatusDto updateUser)
//    public boolean deleteUser(UserRequestDto delUser)
//    public boolean deleteUser(String email)
//    public boolean deleteUser(Long userId)
//    public List<UserCopyEntityDto> getAllUsers()
//    public List<UserCopyEntityDto> getAllUsers1()

    @GetMapping
    public ResponseEntity<List<UserCopyEntityDto>> getAllUsers() {
        List<UserCopyEntityDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.valueOf(200));
    }

    @GetMapping(value = "/get") /////////////////////////
    //public ResponseEntity<UserResponseDto> getUserByName(@RequestParam String name) { ///users/get?name=Other,k=2
    public ResponseEntity<UserResponseDto> getUserByName(@RequestBody UserDto reqUser) { ///users/get?name=Other,k=2
        UserResponseDto user = userService.getUserByEmail(reqUser.getEmail());
        //UserResponseDto user = new UserResponseDto();
                //userService.getUserByName(name);
        return ResponseEntity.status(200).body(user);
    }



    @PutMapping //////////////////////////////
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestUpdateStatusDto updUser) { //update
        //UserResponseDto user = userService.getUserByEmail(updUser.getEmail());
        //user.setStatus(updUser.getStatus());
        UserResponseDto ret = userService.updateUser(updUser);

        return ResponseEntity.status(202).body(ret);
    }

    @DeleteMapping ////////////////////////////////////////
    public ResponseEntity<String> deleteUser(@RequestBody UserDto delUser) { //delete
        Boolean ret = userService.deleteUser(delUser.getEmail());
        HttpStatus status = HttpStatus.OK;
        String resp = "USER: " + delUser.getEmail() + " deleted";
        if (!ret){
            status = HttpStatus.BAD_REQUEST;
            resp = "Error: USER: " + delUser.getEmail() + " NOT deleted";
        }
        return ResponseEntity.status(status).body(resp);
    }

////////////////////////////////////////



    // For testing purpose
    @GetMapping(value = "/test")
    public String testGet(){
        return "Привет, я контроллер - UsersController, " + this.toString();
    }
}
