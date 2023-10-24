package com.example.temperaturerecords.dto;

import com.example.temperaturerecords.entities.UserEntity;
import lombok.Builder;
@Builder
public record ValidateUserByTokenResponseDto(
        boolean validationResult,
        String email,
        UserEntity.Role userRole,
        Long userId
) {}
