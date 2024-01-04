package com.ProjectUPHF.BackFlagUserService.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private String id;
    private String username;
    private String password;
    private int score;
}
