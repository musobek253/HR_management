package uz.muso.hrmanagement.entitiy.enams;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum RoleName {
    ROLE_DIRECTOR(5),// bu dirictor
    ROLE_MANAGER(4),// bu manager
    ROLE_EMPLOYEE(3),
    ROLE_USER(2),
    ROLE_ADMIN(1);



    private int count;


    public int getRoleC() {
        return this.count;
    }
}
