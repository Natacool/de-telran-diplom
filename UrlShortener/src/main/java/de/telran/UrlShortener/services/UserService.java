package de.telran.UrlShortener.services;

import de.telran.UrlShortener.dtos.UserDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
@NoArgsConstructor
public class UserService {
    private List<UserDto> userList;
}
