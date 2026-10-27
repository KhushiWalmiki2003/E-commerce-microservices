package com.ecommerce.user_service.Controller;

import com.ecommerce.user_service.Service.UserService;
import com.ecommerce.user_service.dto.LoginRequestDto;
import com.ecommerce.user_service.dto.LoginResponseDto;
import com.ecommerce.user_service.dto.UserCreateRequestDto;
import com.ecommerce.user_service.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
            @Valid
            @RequestBody UserCreateRequestDto dto){
        UserResponseDto created=userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid
            @RequestBody LoginRequestDto loginRequestDto
            ){
        LoginResponseDto logined=userService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(logined);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Integer id, Authentication authentication){
        UserResponseDto dto=userService.getById(id);
        return ResponseEntity.ok(dto);

    }


}
