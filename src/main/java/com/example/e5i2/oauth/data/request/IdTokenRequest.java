package com.example.e5i2.oauth.data.request;

import jakarta.validation.constraints.NotBlank;

public record IdTokenRequest(@NotBlank String idToken) {}
