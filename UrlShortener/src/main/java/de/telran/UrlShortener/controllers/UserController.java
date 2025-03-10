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
public class UserController implements UserControllerInterface {
    private final UserService userService;

    @Override
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto newUser) {
        UserResponseDto user = userService.createUser(newUser);
        HttpStatus status = HttpStatus.CREATED;
        if (user == null ||
                user.getUserId() == null ||
                user.getPasswordHash() == null ||
                user.getEmail() == null ||
                !user.getEmail().equals(newUser.getEmail())) {
            status = HttpStatus.BAD_REQUEST;
        } else if (user.getPasswordHash().equals("")) {
            status = HttpStatus.CONFLICT;
        }
        return ResponseEntity.status(status).body(user);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<UserCopyEntityDto>> getAllUsers() {
        List<UserCopyEntityDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/get")
    public ResponseEntity<UserResponseDto> getUserByEmail(@RequestBody UserDto reqUser) {
        UserResponseDto user = userService.getUserByEmail(reqUser.getEmail());
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (user != null && user.getUserId() != null){
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(user);
    }

    @Override
    @PutMapping
    public ResponseEntity<UserResponseDto> updateUserStatus(@RequestBody UserRequestUpdateDto updUser) {
        UserResponseDto user = userService.updateUser(updUser);
        HttpStatus status = HttpStatus.OK;
        if (user == null ||
                user.getUserId() == null ||
                user.getStatus() == null ||
                user.getEmail() == null ||
                !user.getEmail().equals(updUser.getEmail()) ){
            status = HttpStatus.BAD_REQUEST;
        } else if (user.getStatus() != updUser.getStatus()){
            status = HttpStatus.NOT_MODIFIED;
        }

        return ResponseEntity.status(status).body(user);
    }

    @Override
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

}
