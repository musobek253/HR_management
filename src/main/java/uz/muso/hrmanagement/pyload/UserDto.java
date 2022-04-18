package uz.muso.hrmanagement.pyload;

import lombok.Data;
import uz.muso.hrmanagement.pyload.RoleDto;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Set;

@Data
public class UserDto implements Serializable {
    private final String username;
    private final String lastName;
    private final String firstName;
    private final String password;
    @Email(message = "Emailni to'g'ri kiriting")
    private final String email;
    private final Set<RoleDto> roles;
}
