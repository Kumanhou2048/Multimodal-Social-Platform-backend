package cyw.usercenter.model.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class GetUserPostRequest implements Serializable {
    private static final long serialVersionUID = 5L;
    int index;
    String userAccount;
}
