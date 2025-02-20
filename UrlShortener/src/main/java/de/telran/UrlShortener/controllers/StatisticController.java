package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.UrlCopyEntityDto;
import de.telran.UrlShortener.dtos.UrlDto;
import de.telran.UrlShortener.dtos.UserCopyEntityDto;
import de.telran.UrlShortener.dtos.UserDto;
import de.telran.UrlShortener.services.StatisticService;
import de.telran.UrlShortener.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/statistic")
public class StatisticController {
    private final StatisticService statisticService;

    // creation statistic, usage statistic
    // user: for this user
    // admin: for all users or specific users
    // [short URLs created for 1,7,30 days] [TOP limit popular/unused]
    // getAllUrls (short, long, amount)
    // getAllUrlsUser(for User)
    // getAllUrlsDetails

/*
    @GetMapping(value = "/urls1")
    public ResponseEntity<List<UrlCopyEntityDto>> getAllUrls1() {
        List<UrlCopyEntityDto> users = statisticService.getAllUrls();
        return new ResponseEntity<>(users, HttpStatus.valueOf(200));
    }
*/
    //@GetMapping  //select
    @GetMapping(value = "/urls")
    public ResponseEntity<List<UrlDto>> getAllUrls() {
        List<UrlDto> urls = new ArrayList<>();
                //statisticService.getAllUrls();
        return new ResponseEntity<>(urls, HttpStatus.valueOf(200));
    }
    @GetMapping(value = "/urls/{userName}")
    public ResponseEntity<UrlDto> getUrlByUser(@RequestParam String userName) { ///users/get?name=Other,k=2
        UrlDto url = new UrlDto();
        //statisticService.getUrlByUser(name);
        return ResponseEntity.status(200).body(url);
    }

    //GetGeneratedUrls(USER, period) – user, admin [statistic] – email, period, generated_amount, (?sort), (?limit)
    //GetClickedUrls(USER, ASC/DESC, period) – user, admin [statistic] - short, clicked_amount, sort, days, limit, long
    //GetAllUrls(USER) – user, admin [statistic] – short, clicked_amount, long
    //GetAllUrls() – admin [statistic] – short, clicked_amount, long

    //////////////////////////////////////
    //GetUrls(USER, mostly clicked/not clicked, ASC/DESC, limit) – user, admin [statistic]



/*
    @GetMapping(value = "/urls/{id}")
    public ResponseEntity<UrlDto> getUserById(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        UserDto user = usersService.getUserById(id);
        return ResponseEntity.status(222).body(user);
    }
*/
    // get [ALL, BLOCKED, ACTIVE, DELETED] users,
    // TOP limit ACTIVE/NON-ACTIVE users
    // short/long format
    // admin:
    // getAllUsers
    // getAllUsersDetails
/*
    @GetMapping(value = "/users1")
    public ResponseEntity<List<UserCopyEntityDto>> getAllUsers1() {
        List<UserCopyEntityDto> users = statisticService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.valueOf(200));
    }
*/
    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = new ArrayList<>();
                //statisticService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.valueOf(200));
    }

    @GetMapping(value = "/users/{userName}")
    public ResponseEntity<UserDto> getUserByName(@RequestParam String userName) {
        UserDto user = new UserDto();
        //statisticService.getAllUsers();
        return new ResponseEntity<>(user, HttpStatus.valueOf(200));
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserDto> getUserById(@RequestParam Long id) {
        UserDto user = new UserDto();
        //statisticService.getAllUsers();
        return new ResponseEntity<>(user, HttpStatus.valueOf(200));
    }
    // For testing purpose
    @GetMapping(value = "/test")
    public String testGet(){
        return "Привет, я контроллер - StatisticController, " + this.toString();
    }

    //GetAllUsers() – admin [statistic] – email, status, role, generated_amount

    //////////////////////////////////////
    //GetUsers(active/not_active, role, status, ASC/DESC, limit) – admin [statistic]
    //GetUserInfo(user_id) – admin [statistic]





}
