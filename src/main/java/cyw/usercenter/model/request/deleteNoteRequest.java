package cyw.usercenter.model.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class deleteNoteRequest implements Serializable {
    private static final long serialVersionUID = 9L;
    private int id;
}
