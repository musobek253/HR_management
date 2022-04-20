package uz.muso.hrmanagement.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muso.hrmanagement.entitiy.Company;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
    boolean existsByName(String CompanyName);
}
