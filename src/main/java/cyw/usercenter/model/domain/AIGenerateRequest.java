package cyw.usercenter.model.domain;

import java.io.Serializable;
import lombok.Data;
@Data
public class AIGenerateRequest {
    private static final long serialVersionUID = 1L;
    private String title;
    private String imageBase64;
}
