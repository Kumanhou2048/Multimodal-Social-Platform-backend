package cyw.usercenter.model.request;

import lombok.Data;

import java.util.List;

@Data
public class GetPostPictureRequest {
    private List<String> url;
}
