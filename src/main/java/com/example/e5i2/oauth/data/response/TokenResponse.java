package com.example.e5i2.oauth.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TokenResponse(
        @NotBlank @JsonProperty("accessToken") String accessToken,
        @NotBlank @JsonProperty("accessTokenExpiration") long accessTokenExpiration) {}
