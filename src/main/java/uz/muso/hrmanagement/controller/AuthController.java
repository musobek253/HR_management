package uz.muso.hrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import uz.muso.hrmanagement.pyload.ApiResponse;
import uz.muso.hrmanagement.pyload.LoginDto;
import uz.muso.hrmanagement.pyload.UserDto;
import uz.muso.hrmanagement.service.MyAuthService;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    MyAuthService myAuthService;


    @PostMapping("/login")
    public HttpEntity<ApiResponse> login(@RequestBody LoginDto loginDto) {
        ApiResponse login = myAuthService.login(loginDto);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/addAdmin")// admin qo'shadi yani sistemga company va directorlarni qo'shish uchun
    public ResponseEntity<ApiResponse> addAdmin(@RequestBody UserDto userDto) throws MessagingException {
        ApiResponse apiResponse = myAuthService.addAdmin(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping("/user/verifyEmail")
// bu userdan ketgan parametrlarni get orqali olib bazada bor yoki yo'qligini tekshiradi
    public HttpEntity<?> verifyEmail(@RequestParam String email, @RequestParam String code) {
        ApiResponse apiResponse = myAuthService.verifyEmail(email, code);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }
}
