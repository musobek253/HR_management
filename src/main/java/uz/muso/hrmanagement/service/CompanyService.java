package uz.muso.hrmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.muso.hrmanagement.component.Checker;
import uz.muso.hrmanagement.entitiy.Company;
import uz.muso.hrmanagement.entitiy.Role;
import uz.muso.hrmanagement.entitiy.User;
import uz.muso.hrmanagement.pyload.ApiResponse;
import uz.muso.hrmanagement.pyload.CompanyDto;
import uz.muso.hrmanagement.repositoriy.CompanyRepository;
import uz.muso.hrmanagement.repositoriy.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Checker checker;

    public ApiResponse addCompany(CompanyDto companyDto){
        if (companyRepository.existsByName(companyDto.getName()))
            return new ApiResponse("Already exist by Company name",false);
        UUID directorId = companyDto.getDirectorId();
        Optional<User> userOptional = userRepository.findById(directorId);
        if (!userOptional.isPresent())
            return new ApiResponse("Director  not fount",false);
        User user = userOptional.get();
        Set<Role> roles = user.getRoles();
        boolean checkDirector = checker.checkDirector(roles);
        if (!checkDirector)
            return new ApiResponse("bu user companiy directori emas",false);

        Company company = new Company();
        company.setDirector(user);
        company.setName(companyDto.getName());
        companyRepository.save(company);
            return new ApiResponse("Company Succsessfull add",false);


    }

}
