package com.clickpick.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FindIdReq {
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
}
