package com.ProjectUPHF.BackFlagUserService.mapper;

import com.ProjectUPHF.BackFlagUserService.api.dto.UserDto;
import com.ProjectUPHF.BackFlagUserService.api.response.UserResponse;
import com.ProjectUPHF.BackFlagUserService.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserMapper {


    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId().toHexString())
                .username(user.getUsername())
                .password(user.getPassword())
                .score(user.getScore())
                .build();
    }

    public UserResponse toResponse(List<User> users) {


        final List<UserDto> dtos = users.stream()
                .map(this::toDto)
                .toList();

        return UserResponse.builder()
                .users(dtos)
                .build();
    }
    public UserResponse toResponse(User user) {
        UserDto dto = toDto(user);

        return UserResponse.builder()
                .users(List.of(dto))
                .build();
    }
}
