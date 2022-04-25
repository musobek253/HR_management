package uz.muso.hrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.muso.hrmanagement.pyload.ApiResponse;
import uz.muso.hrmanagement.pyload.UserDto;
import uz.muso.hrmanagement.service.UserService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService userService;

    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER','ROLE_DIRECTOR')")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody UserDto userDto) throws MessagingException {

        ApiResponse response = userService.addUser(userDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/addDrictor")
    public ResponseEntity<ApiResponse> addDirector(UserDto userDto) throws MessagingException {
        ApiResponse apiResponse = userService.addDrictor(userDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 201:409).body(apiResponse);
    }
}
