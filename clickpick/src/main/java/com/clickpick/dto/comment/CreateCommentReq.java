package com.clickpick.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCommentReq {

    @NotBlank
    private Long postId;

    @NotBlank
    private String content;

}
