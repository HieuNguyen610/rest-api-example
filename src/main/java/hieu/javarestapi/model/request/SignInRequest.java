package hieu.javarestapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest implements Serializable {

    private String username;
    private String password;
    private String platform; // web, mobile, miniApp
    private String deviceToken; // for push notification
    private String versionApp;
}
