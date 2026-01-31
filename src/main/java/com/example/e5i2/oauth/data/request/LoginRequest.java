package com.example.e5i2.oauth.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank @JsonProperty("email") String email,
        @NotBlank @JsonProperty("password") String password) {}
