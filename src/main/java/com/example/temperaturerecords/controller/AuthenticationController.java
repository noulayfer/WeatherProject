package com.example.temperaturerecords.controller;

import com.example.temperaturerecords.dto.LoginRequestDto;
import com.example.temperaturerecords.dto.LoginResponseDto;
import com.example.temperaturerecords.dto.ValidateUserByTokenResponseDto;
import com.example.temperaturerecords.service.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;


    @PostMapping("/signin")
    public LoginResponseDto authenticateUser(@RequestBody @Valid LoginRequestDto loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping()
    public String getSome() {
        return "hello";
    }

    @PostMapping("/refresh")
    public LoginResponseDto refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
        return authService.refreshJwt(refreshToken);
    }


    @PostMapping("/validUser")
    public ValidateUserByTokenResponseDto validateUserByToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {
        return authService.validateUserByToken(token);
    }
}
