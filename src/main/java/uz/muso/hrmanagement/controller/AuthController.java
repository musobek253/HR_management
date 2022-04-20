package uz.muso.hrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.muso.hrmanagement.pyload.ApiResponse;
import uz.muso.hrmanagement.pyload.LoginDto;
import uz.muso.hrmanagement.security.JwtProvider;
import uz.muso.hrmanagement.service.MyAuthService;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    MyAuthService myAuthService;


    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto) {
        ApiResponse login = myAuthService.login(loginDto);
        return ResponseEntity.status(login.isSuccess()?201:409).body(login);
    }

}
