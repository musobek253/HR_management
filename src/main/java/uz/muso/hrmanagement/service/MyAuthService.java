package uz.muso.hrmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.muso.hrmanagement.entitiy.User;
import uz.muso.hrmanagement.pyload.ApiResponse;
import uz.muso.hrmanagement.pyload.LoginDto;
import uz.muso.hrmanagement.repositoriy.UserRepository;
import uz.muso.hrmanagement.security.JwtProvider;

import java.util.Optional;

@Service
public class MyAuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;

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
}
