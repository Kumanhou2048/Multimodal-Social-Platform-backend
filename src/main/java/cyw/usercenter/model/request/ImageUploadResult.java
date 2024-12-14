package cyw.usercenter.model.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class ImageUploadResult implements Serializable {
    private static final long serialVersionUID = 10L;
    private String url;
    private String errorMessage;
}
