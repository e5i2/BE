package com.example.e5i2.oauth.data.request;

import com.example.e5i2.user.domain.GenderType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupRequest(
        @NotBlank @Email(message = "유효하지 않은 이메일 형식입니다.") @JsonProperty("email") String email,
        @NotBlank @JsonProperty("nickname") String nickname,
        @NotNull @JsonProperty("gender") GenderType gender,
        @NotNull @JsonProperty("height") Double height,
        @NotNull @JsonProperty("weight") Double weight
        ) {}
