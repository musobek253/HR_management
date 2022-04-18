package uz.muso.hrmanagement.entitiy.enams;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum RoleName {
    ROLE_DIRECTOR(4),// bu dirictor
    ROLE_MANAGER(3),// bu manager
    ROLE_EMPLOYEE(2),
    ROLE_USER(1);


    private int count;


    public int getRoleC() {
        return this.count;
    }
}
