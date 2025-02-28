package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.*;
import de.telran.UrlShortener.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // User APIs
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto newUser) {
        UserResponseDto user = userService.createUser(newUser);
        HttpStatus status = HttpStatus.CREATED;
        if (user.getUserId() == null) {
            status = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(status).body(user);
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
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<UserResponseDto> getUserByEmail(@RequestBody UserDto reqUser) {
        UserResponseDto user = userService.getUserByEmail(reqUser.getEmail());
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (user != null && user.getUserId() != null){
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(user);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUserStatus(@RequestBody UserRequestUpdateStatusDto updUser) {
        UserResponseDto user = userService.updateUser(updUser);
        HttpStatus status = HttpStatus.NOT_MODIFIED;
        if (user != null && user.getUserId() != null){
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(user);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody UserDto delUser) {
        Boolean ret = userService.deleteUser(delUser.getEmail());
        HttpStatus status = HttpStatus.OK;
        String resp = "USER: " + delUser.getEmail() + " deleted";
        if (!ret){
            status = HttpStatus.BAD_REQUEST;
            resp = "Error: USER: " + delUser.getEmail() + " NOT deleted";
        }
        return ResponseEntity.status(status).body(resp);
    }
/*
    // For testing purpose
    @GetMapping(value = "/test")
    public String testGet(){
        return "Привет, я контроллер - UsersController, " + this.toString();
    }
*/
}
