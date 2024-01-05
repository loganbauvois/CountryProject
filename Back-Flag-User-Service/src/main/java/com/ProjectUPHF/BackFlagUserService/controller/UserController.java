package com.ProjectUPHF.BackFlagUserService.controller;

import com.ProjectUPHF.BackFlagUserService.api.dto.UserDto;
import com.ProjectUPHF.BackFlagUserService.api.request.UserCreationRequest;
import com.ProjectUPHF.BackFlagUserService.api.response.UserResponse;
import com.ProjectUPHF.BackFlagUserService.entity.User;
import com.ProjectUPHF.BackFlagUserService.mapper.UserMapper;
import com.ProjectUPHF.BackFlagUserService.repository.UserRepository;
import com.ProjectUPHF.BackFlagUserService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    @GetMapping
    @Operation(
            summary = "get all users",
            description = "Get all users.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Response if the users have been found",
                            content = @Content(mediaType = "application/json", schema = @Schema(allOf = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Response if there is no users",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Response if there is a problem with the server",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<UserResponse> getAllUsers() {
        final List<User> users = userService.getAll();
        if(users != null){
            if(users.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }else{
                final UserResponse response = userMapper.toResponse(users);

                return ResponseEntity.ok(response);
            }
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    @GetMapping("/{username}/{password}")
    @Operation(
            summary = "Check if the user use the good password",
            description = "Check if the user use the good password.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Response if the user have been found",
                            content = @Content(mediaType = "application/json", schema = @Schema(allOf = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Response if the user is not found",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<Boolean> getUserByName(@PathVariable("username") String username, @PathVariable("password") String password) {
        final User user = userService.getUserByName(username);
        if (user == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(user.getPassword().equals(password));
    }

    @GetMapping("/classement")
    @Operation(
            summary = "get the ranking",
            description = "Get the ranking",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Response if the ranking have been found",
                            content = @Content(mediaType = "application/json", schema = @Schema(allOf = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Response if there is no people to rank",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Response if there is a problem with the server",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<List<UserDto>> getClassement(){

        final List<User> list = userService.getClassement();
        if(list != null) {
            if (list.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.ok(list.stream().map(userMapper::toDto).toList());
            }
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/scoreUpdate")
    @Operation(
            summary = "Update the score",
            description = "Update the score if the current score is better then the best",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Score updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(allOf = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Response if there is username",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<Void> checkResponse(@RequestParam(required = true) String username, @RequestParam(required = true) Integer score){
        if(userRepository.findByUsername(username) != null){
            userService.updateScore(username, score);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/score")
    @Operation(
            summary = "Get score",
            description = "Get the score .",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "got the Score",
                            content = @Content(mediaType = "application/json", schema = @Schema(allOf = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Response if there is  no user with username",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<Integer> getScore(@RequestParam(required = true) String username){
        if(userRepository.findByUsername(username) != null){

            return ResponseEntity.ok().body(userService.getScore(username));
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    @Operation(
            summary = "Create a user",
            description = "Create a user with the provided data.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Response if the user was successfully created",
                            content = @Content(mediaType = "application/json", schema = @Schema(allOf = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Response if the provided username is already used",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Response if the provided data is not valid",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest request) {

        if(request.getUsername() != null & request.getPassword() != null){
            final User user = userService.create(request);

            if(user != null){
                final UserDto dto = userMapper.toDto(user);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            }
            else{
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


    }


    @PutMapping("/{username}")
    @Operation(
            summary = "Update a user",
            description = "Update a user with the provided data.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Response if the user was successfully updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(allOf = UserDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Response if the provided username is already used or the user to update doesn't exist",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Response if the provided data is not valid",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<?> updateUser(@RequestBody UserCreationRequest request, @PathVariable("username") String username) {
        if(request.getUsername() != null && request.getPassword() != null && username != null){
            final User user = userService.update(request, username);
            if(user != null){
                final UserDto dto = userMapper.toDto(user);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            }else{
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


    }

    @DeleteMapping
    @Operation(
            summary = "Delete all users",
            description = "delete all users.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Response if the suppression is complete"
                    )
            }
    )
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok("Tous les utilisateurs ont été supprimés avec succès !");
    }
}
