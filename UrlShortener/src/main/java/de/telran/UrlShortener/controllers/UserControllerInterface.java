package de.telran.UrlShortener.controllers;

import de.telran.UrlShortener.dtos.*;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "USERs", description = "Controller to work with users",
        externalDocs = @ExternalDocumentation(
                description = "Link to extended documentation about USER component",
                url = "https://github.com/Natacool/de-telran-diplom/blob/main/README.md"
        )
)
public interface UserControllerInterface {

        @Operation(
                summary = "Create a new user",
                description = "This operation creates a new user"
        )
        ResponseEntity<UserResponseDto> createUser(
                @Parameter(description = "Parameters to create a new user"
                        , required = true
                )
                UserRequestDto newUser
        );

        @Operation(
                summary = "Get all DB entries from DB USERs table",
                description = "This operation gets all DB USERs entries with all columns, hiding user's password"
        )
        ResponseEntity<List<UserCopyEntityDto>> getAllUsers();

        @Operation(
                summary = "Get user entry from DB USERs table",
                description = "This operation gets a specific user entry with all columns, hiding the user's password"
        )
        ResponseEntity<UserResponseDto> getUserByEmail(
                @Parameter(description = "Parameters to get short URL, if exists"
                        , required = true
                )
                UserDto reqUser
        );

        @Operation(
                summary = "Update user's status",
                description = "This operation updates user's status"
        )
        ResponseEntity<UserResponseDto> updateUserStatus(
                @Parameter(description = "Parameters to updates user's status"
                        , required = true
                )
                UserRequestUpdateDto updUser
        );

        @Operation(
                summary = "Delete user",
                description = "This operation user, move the USER to DELETED status"
        )
        ResponseEntity<String> deleteUser(
                @Parameter(description = "Parameter to delete a user, if exists"
                        , required = true
                )
                UserDto delUser
        );
}
