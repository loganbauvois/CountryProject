package com.ProjectUPHF.BackFlagUserService.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationRequest {
    private String username;
    private String password;
}
