package cyw.usercenter.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class GetDetailedNotesRequest {
    String posterAvatarUrl;
    String posterName;
    String postTime;
    String postTitle;
    String postContent;
    String userAccount;
    int like;
}
