package com.clickpick.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FindPwReq {

    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
}
