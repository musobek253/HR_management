package uz.muso.hrmanagement.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muso.hrmanagement.entitiy.User;

import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findAllByEmail(@Email(message = "Emailni to'g'ri kiriting") String email);
    boolean existsAllByEmail(@Email(message = "Emailni to'g'ri kiriting") String email);
}