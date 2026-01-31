package com.example.e5i2.oauth.data.response;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginResponse(
        Long memberId,
        String email,
        String nickname,
        @NotBlank @JsonProperty("tokenResponse") TokenResponse tokenResponse) {}
