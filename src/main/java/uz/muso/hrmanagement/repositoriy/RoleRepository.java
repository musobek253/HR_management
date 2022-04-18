package uz.muso.hrmanagement.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muso.hrmanagement.entitiy.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}