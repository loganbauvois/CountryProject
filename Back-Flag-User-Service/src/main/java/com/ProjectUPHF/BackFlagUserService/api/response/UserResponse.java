package com.ProjectUPHF.BackFlagUserService.api.response;

import com.ProjectUPHF.BackFlagUserService.api.dto.UserDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private List<UserDto> users;
}
