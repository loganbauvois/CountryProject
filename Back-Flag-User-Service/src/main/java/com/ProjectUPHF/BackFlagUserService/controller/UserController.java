package com.ProjectUPHF.BackFlagUserService.controller;

import com.ProjectUPHF.BackFlagUserService.api.dto.UserDto;
import com.ProjectUPHF.BackFlagUserService.api.request.UserCreationRequest;
import com.ProjectUPHF.BackFlagUserService.api.response.UserResponse;
import com.ProjectUPHF.BackFlagUserService.entity.User;
import com.ProjectUPHF.BackFlagUserService.mapper.UserMapper;
import com.ProjectUPHF.BackFlagUserService.service.UserService;
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

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserResponse> getAllUsers() {
        final List<User> users = userService.getAll();
        final UserResponse response = userMapper.toResponse(users);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}/{password}")
    public boolean getUserByName(@PathVariable("username") String username, @PathVariable("password") String password) {
        final User user = userService.getUserByName(username);
        if(user.getPassword().equals(password)){
            return true;
        }else return false;

    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest request) {
        final User user = userService.create(request);

        if(user != null){
            final UserDto dto = userMapper.toDto(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le nom d'utilisateur existe déjà.");

        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody UserCreationRequest request, @PathVariable("username") String username) {
        final User user = userService.update(request, username);

            final UserDto dto = userMapper.toDto(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);


    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok("Tous les utilisateurs ont été supprimés avec succès !");
    }
}
