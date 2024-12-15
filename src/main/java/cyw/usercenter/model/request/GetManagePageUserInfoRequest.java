package cyw.usercenter.model.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class GetManagePageUserInfoRequest implements Serializable {
    private static final long serialVersionUID = 11L;
    private String userAccount;
}
