package com.clickpick.dto.post;

import com.clickpick.domain.PostCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePostReq {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String postCategory;

    private String position;

    private String hashtag;
}
