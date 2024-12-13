package cyw.usercenter.model.request;

import lombok.Data;

@Data
public class makeACommentRequest {
    private int userId;
    private int postId;
    private String content;

}
