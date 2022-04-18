package uz.muso.hrmanagement.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muso.hrmanagement.entitiy.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}