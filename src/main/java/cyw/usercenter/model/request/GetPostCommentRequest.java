package cyw.usercenter.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class GetPostCommentRequest {
    String avatarUrl;
    String name;
    String content;
    String time;

}
