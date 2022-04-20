package uz.muso.hrmanagement.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.muso.hrmanagement.component.Checker;
import uz.muso.hrmanagement.component.GMailSender;
import uz.muso.hrmanagement.component.PasswordGenerator;
import uz.muso.hrmanagement.entitiy.Role;
import uz.muso.hrmanagement.entitiy.User;
import uz.muso.hrmanagement.pyload.ApiResponse;
import uz.muso.hrmanagement.pyload.UserDto;
import uz.muso.hrmanagement.repositoriy.RoleRepository;
import uz.muso.hrmanagement.repositoriy.UserRepository;

import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class UserService {

    @Autowired
     RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Checker checker;
    @Autowired
    PasswordGenerator passwordGenerator;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    GMailSender mailSender;

    @Autowired
    TrinketService trinketService;

    public ApiResponse addUser(UserDto userDto)throws MessagingException {
        Optional<Role> roleOptional = roleRepository.findById(userDto.getRoleId());
        if (!roleOptional.isPresent()){
            return new ApiResponse("Role id not found",false);
        }
        boolean check = checker.check(roleOptional.get().getRoleName().name());
        if(!check){
            return new ApiResponse("bu rol bilan bu Vazifani bajara olmaysiz",false);
        }
        boolean byEmail = userRepository.existsAllByEmail(userDto.getEmail());
        if (!byEmail)
            return new ApiResponse("Already exis by email",false);
        User user = new User();

        user.setEmail(userDto.getEmail());
        user.setPosition(userDto.getPosition());

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleOptional.get());
        user.setRoles(roleSet);

        String password = passwordGenerator.randomPassword(8);
        user.setPassword(passwordEncoder.encode(password));

        String code = UUID.randomUUID().toString();
        user.setVerifyCode(code);

        User userDatabase = userRepository.save(user);
        boolean mailTextAddEmployer = mailSender.mailTextAddEmployer(userDatabase.getEmail(), code, userDatabase.getPassword());
        if (mailTextAddEmployer)
            return  new ApiResponse("user successfully added send password email verified",true);
        else return new ApiResponse("not found",false);
    }


}
