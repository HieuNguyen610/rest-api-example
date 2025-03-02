package hieu.javarestapi.model.request;

import hieu.javarestapi.common.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {
    @NotBlank(message = "firstName must not be blank")
    private String firstName;

    @NotBlank(message = "lastName must not be blank")
    private String lastName;

    private Gender gender;

    private Date birthday;

    @NotBlank(message = "username must not be blank")
    private String username;

    @Email(message = "Email invalid")
    private String email;

    @NotBlank(message = "phone must not be blank")
    private String phone;

    private String password;
    private String passwordConfirm;
}
