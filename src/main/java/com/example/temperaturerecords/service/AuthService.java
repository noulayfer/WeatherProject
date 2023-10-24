package com.example.temperaturerecords.service;

import com.example.temperaturerecords.config.JwtUtils;
import com.example.temperaturerecords.config.UserContext;
import com.example.temperaturerecords.dto.LoginRequestDto;
import com.example.temperaturerecords.dto.LoginResponseDto;
import com.example.temperaturerecords.dto.ValidateUserByTokenResponseDto;
import com.example.temperaturerecords.entities.UserEntity;
import com.example.temperaturerecords.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password()));

        String subject = ((UserContext) authentication.getPrincipal()).getEmail();
        userRepository.findUserEntityByEmail(subject).orElseThrow();
        log.info("Successful login-pass authentication, subject: {}", subject);

        return generateTokens(subject);
    }

    public LoginResponseDto refreshJwt(String authHeader) {
        var token = jwtUtils.getJwtFromAuthHeader(authHeader)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token is not specified"));

        if (!jwtUtils.isValidRefreshToken(token)) {
            throw new AuthenticationServiceException("Refresh token is not valid");
        }
        String subject = jwtUtils.getSubjectFromToken(token);
        log.info("Reissued access and refresh tokens, subject: {}", subject);

        return generateTokens(subject);
    }

    public ValidateUserByTokenResponseDto validateUserByToken(String authHeader) {
        var token = jwtUtils.getJwtFromAuthHeader(authHeader)
                .orElseThrow(() -> new AuthenticationServiceException("Token is not specified"));

        if (!jwtUtils.isValidAccessToken(token)) {
            throw new AuthenticationServiceException("Token is not valid");
        }

        var email = jwtUtils.getSubjectFromToken(token);

        UserEntity userEntity = userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("There is no user in the database: " + email));
        log.info("Getting userId: {}", userEntity.getId());

        UserEntity.Role userRole = userEntity.getRole();
        log.info("Getting userRole: {}", userRole.name());

        return ValidateUserByTokenResponseDto.builder()
                .validationResult(true)
                .email(email)
                .userRole(userRole)
                .userId(userEntity.getId())
                .build();
    }

    private LoginResponseDto generateTokens(String email) {
        var accessToken = jwtUtils.generateAccessToken(email);
        var refreshToken = jwtUtils.generateRefreshToken(email);

        return new LoginResponseDto(accessToken, refreshToken);
    }
}