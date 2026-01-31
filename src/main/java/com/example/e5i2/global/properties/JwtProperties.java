package com.example.e5i2.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt.token")
public record JwtProperties(String secret, TokenDetails access, TokenDetails refresh) {
	public record TokenDetails(long expiration) {}
}