package cyw.usercenter.model.request;

import lombok.Data;

@Data
public class PostLikesRequest {
    private int userID;
    private int postID;
    private boolean status;
}
