package com.example.e5i2.oauth.data.response;

import com.example.e5i2.user.domain.GenderType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record SignupResponse(
		@JsonProperty("userId") Long userId,
        @JsonProperty("email") String email,
        @JsonProperty("nickname") String nickname,
        @JsonProperty("gender") GenderType gender,
		@JsonProperty("height") Double height,
		@JsonProperty("weight") Double weight,
        @JsonProperty("tokenResponse") TokenResponse tokenResponse) {}
