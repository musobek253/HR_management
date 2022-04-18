package uz.muso.hrmanagement.pyload;

import lombok.Data;
import uz.muso.hrmanagement.entitiy.enams.RoleName;

import java.io.Serializable;

@Data
public class RoleDto implements Serializable {
    private  RoleName roleName;
}
