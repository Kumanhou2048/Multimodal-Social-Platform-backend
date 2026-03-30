package cyw.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class NotesUploadRequest implements Serializable {
    private static final long serialVersionUID = 11L;
    String userAccount;
    String title;
    String content;
    int noteType; //0图文，1视频
    int imageCount; //图片的数量
    List<String> imageUrls;
    Boolean isAiGenerated;
}
