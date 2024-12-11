package cyw.usercenter.model.request;

import lombok.Data;

import java.util.List;
@Data
public class NotesUploadRequest {
    String userAccount;
    String title;
    String content;
    int noteType;
    int imageCount;
    List<String> imageUrl;

}
