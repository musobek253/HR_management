package uz.muso.hrmanagement.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.muso.hrmanagement.entitiy.Role;
import uz.muso.hrmanagement.entitiy.User;
import uz.muso.hrmanagement.entitiy.enams.RoleName;
import uz.muso.hrmanagement.repositoriy.UserRepository;

import java.util.Optional;
import java.util.Set;

@Component
public class Checker {
    @Autowired
    UserRepository userRepository;

    public boolean check(String role){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()){
            Set<Role> roles = userOptional.get().getRoles();
            String position = userOptional.get().getPosition();
            if (role.equals(RoleName.ROLE_DIRECTOR.name())) return false;
            for (Role roleApp : roles) {
               if ((role.equals(RoleName.ROLE_MANAGER.name())) &&
                       (roleApp.getRoleName().name().equals(RoleName.ROLE_DIRECTOR.name())))
                   return true;
               if ((role.equals(RoleName.ROLE_EMPLOYEE.name()))&&
                       (position.equalsIgnoreCase("HR_MANAGER"))||
                       (roleApp.getRoleName().name().equals(RoleName.ROLE_DIRECTOR.name())))
                   return true;
            }
        }
        return false;
    }
    public boolean checkEmployee() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()) {
            Set<Role> roles = userOptional.get().getRoles();
            String position = userOptional.get().getPosition();
            for (Role roleApp : roles) {
                if (roleApp.getRoleName().name().equals(RoleName.ROLE_DIRECTOR.name())) {
                    return true;
                } else return roleApp.getRoleName().name().equals(RoleName.ROLE_MANAGER.name());
            }
        }
        return false;
    }
    public boolean checkDirector(Set<Role>roles) {
            for (Role roleApp : roles) {
                if (roleApp.getRoleName().name().equals(RoleName.ROLE_DIRECTOR.name()))
                    return true;
            }
        return false;
    }
    public boolean checkAdmin() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()) {
            Set<Role> roles = userOptional.get().getRoles();
            for (Role roleApp : roles) {
                if (roleApp.getRoleName().name().equals(RoleName.ROLE_ADMIN.name()))
                    return true;
            }

        }
        return false;
    }
}

