package uz.muso.hrmanagement.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muso.hrmanagement.entitiy.Trinket;
import uz.muso.hrmanagement.entitiy.User;

import java.util.Optional;
import java.util.UUID;

public interface TrinketRepository extends JpaRepository<Trinket, UUID> {
    Optional<Trinket> findAllByOwner(User owner);

    Optional<Trinket> findByNumber(String number);
}