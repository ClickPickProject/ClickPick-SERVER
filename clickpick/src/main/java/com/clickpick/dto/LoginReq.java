package com.clickpick.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginReq {

    @NotBlank
    private String id;

    @NotBlank
    private String password;
}
