package cyw.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetUserPostNumRequest implements Serializable {
    private static final long serialVersionUID = 6L;
    String userAccount;
}
