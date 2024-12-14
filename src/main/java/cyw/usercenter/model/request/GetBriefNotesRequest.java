package cyw.usercenter.model.request;

import lombok.Data;

@Data
public class GetBriefNotesRequest {
    private int id;
    private String imageUrl;
    private String avatarUrl;
    private String title;
    private String username;
    private int likes;
}
