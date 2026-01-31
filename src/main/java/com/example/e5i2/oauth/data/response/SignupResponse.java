package com.example.e5i2.oauth.data.response;

import com.example.e5i2.user.domain.GenderType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SignupResponse(
        @NotBlank @JsonProperty("email") String email,
        @NotBlank @JsonProperty("nickname") String nickname,
        @NotNull @JsonProperty("gender") GenderType gender,
		@NotBlank @JsonProperty("height") Double height,
		@NotBlank @JsonProperty("weight") Double weight,
        @NotBlank @JsonProperty("tokenResponse") TokenResponse tokenResponse) {}
