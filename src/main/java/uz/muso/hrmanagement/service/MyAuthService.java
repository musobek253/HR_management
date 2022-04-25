package uz.muso.hrmanagement.service;

import antlr.BaseAST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.muso.hrmanagement.component.Checker;
import uz.muso.hrmanagement.component.GMailSender;
import uz.muso.hrmanagement.component.PasswordGenerator;
import uz.muso.hrmanagement.entitiy.Role;
import uz.muso.hrmanagement.entitiy.User;
import uz.muso.hrmanagement.pyload.ApiResponse;
import uz.muso.hrmanagement.pyload.LoginDto;
import uz.muso.hrmanagement.pyload.UserDto;
import uz.muso.hrmanagement.repositoriy.RoleRepository;
import uz.muso.hrmanagement.repositoriy.UserRepository;
import uz.muso.hrmanagement.security.JwtProvider;

import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class MyAuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    Checker checker;
    @Autowired
    GMailSender mailSender;
    @Autowired(required = false)
    RoleRepository roleRepository;
    @Autowired
    PasswordGenerator passwordGenerator;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> allByEmail = userRepository.findAllByEmail(email);
        return allByEmail.orElse(null);
    }

    public ApiResponse login(LoginDto loginDto) {
        try {
            //shunaqa tizim odami bormi tekshirish
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRoles());
            return new ApiResponse("Token", false, token);

        } catch (BadCredentialsException e) {
            return new ApiResponse("Login yoki Paroliz notogri!", false);
        }
    }

    public ApiResponse verifyEmail(String email, String code) {
        Optional<User> optionalUser = userRepository.findByEmailAndVerifyCode(email, code);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setVerifyCode(null);
            userRepository.save(user);
            return new ApiResponse("Account Verified and Turniket is Given",true);
        }
        return new ApiResponse("Account not found",false);
    }


    public ApiResponse addAdmin(UserDto userDto) throws MessagingException {

        boolean byEmail = userRepository.existsAllByEmail(userDto.getEmail());
        if (byEmail)
            return new ApiResponse("Already exis by email",false);
        Optional<Role> roleOptional = roleRepository.findById(userDto.getRoleId());
        if (!roleOptional.isPresent()){
            return new ApiResponse("Role not found",false);
        }
        Role role = roleOptional.get();
        String name = role.getRoleName().name();
        if(!name.equals("ROLE_ADMIN"))
            return new ApiResponse("Siz faqat Admin va director lavozimini qo'sha olasiz",false);
        User user = new User();

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPosition(userDto.getPosition());

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRoles(roleSet);

        String password = passwordGenerator.randomPassword(8);
        user.setPassword(passwordEncoder.encode(password));

        String code = UUID.randomUUID().toString();
        user.setVerifyCode(code);

        User userDatabase = userRepository.save(user);
        boolean mailTextAddEmployer = mailSender.mailTextAddEmployer(userDatabase.getEmail(), code, password);
        if (mailTextAddEmployer)
            return  new ApiResponse("Admin successfully added send password email verified",true);
        else return new ApiResponse("not found",false);
    }
}

