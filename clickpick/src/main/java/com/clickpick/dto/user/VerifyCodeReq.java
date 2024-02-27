package com.clickpick.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyCodeReq {

    @NotBlank
    private String id;
    @NotBlank
    private String code;

}
