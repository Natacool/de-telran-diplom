package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.*;
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

    @GetMapping
    public ResponseEntity<List<UserCopyEntityDto>> getAllUsers() {
        List<UserCopyEntityDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.valueOf(200));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        UserDto user = new UserDto();
        //userService.getUserById(id);
        return ResponseEntity.status(222).body(user);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<UserDto> getUserByName(@RequestParam String name) { ///users/get?name=Other,k=2
        UserDto user = new UserDto();
                //userService.getUserByName(name);
        return ResponseEntity.status(200).body(user);
    }

    @PostMapping(value = "/create") //Jackson
    public ResponseEntity<Boolean> createUser(@RequestBody UserDto newUser) { //insert
        boolean res = false;
                //userService.createUser(newUser);
        return ResponseEntity.status(201).body(res);
    }

    @PostMapping //Jackson
    public ResponseEntity<UserResponseDto> insertUser(@RequestBody UserDto newUser) { //insert
        //UserDto user = new UserDto();
                //userService.insertUser(newUser);
        UserRequestDto user = new UserRequestDto();
        userService.createUser(user);

        UserResponseDto user1 = new UserResponseDto();
        return ResponseEntity.status(201).body(user1);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserDto updUser) { //update
        UserDto user = new UserDto();
        UserRequestUpdateStatusDto user1 = new UserRequestUpdateStatusDto();
        userService.updateUser(user1);
        UserResponseDto user2 = new UserResponseDto();
        //userService.updateUser(updUser);
        return ResponseEntity.status(202).body(user2);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable Long id) { //delete
        UserDto delUser = new UserDto();
                //userService.getUserById(id);
        UserRequestDto user = new UserRequestDto();
        userService.deleteUser(user);
        UserResponseDto user1 = new UserResponseDto();

        return ResponseEntity.status(204).body(user1);
    }

    @DeleteMapping(value = "/{user}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable UserRequestDto user) { //delete
        UserDto delUser = new UserDto();
        //userService.getUserById(id);
        UserRequestDto user11 = new UserRequestDto();
        userService.deleteUser(user);
        UserResponseDto user1 = new UserResponseDto();

        return ResponseEntity.status(204).body(user1);
    }

    @DeleteMapping(value = "/{email}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable String email) { //delete
        UserDto delUser = new UserDto();
        //userService.getUserById(id);
        UserRequestDto user = new UserRequestDto();
        userService.deleteUser(user);
        UserResponseDto user1 = new UserResponseDto();

        return ResponseEntity.status(204).body(user1);
    }

    // For testing purpose
    @GetMapping(value = "/test")
    public String testGet(){
        return "Привет, я контроллер - UsersController, " + this.toString();
    }
}
