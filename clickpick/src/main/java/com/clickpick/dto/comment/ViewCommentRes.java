package com.clickpick.dto.comment;

import com.clickpick.domain.Comment;
import com.clickpick.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ViewCommentRes {
    @NotNull
    private Long commentId;
    @NotBlank
    private String nickname;
    @NotBlank
    private String content;
    @NotBlank
    private LocalDateTime createAt;
    @NotNull
    private Long likeCount;
    private boolean likeCommentCheck;

    public ViewCommentRes(Comment comment, boolean likeCommentCheck) {
        this.commentId = comment.getId();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.createAt = comment.getCreateAt();
        this.likeCount = (long) comment.getCommentLikes().size();
        this.likeCommentCheck = likeCommentCheck;
    }
}
