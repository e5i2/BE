package com.example.e5i2.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "oauth.google")
public record GoogleOauthProperties(
	String clientId,
	String clientSecret,
	String redirectUri,
	String tokenUri,
	String userInfoUri) {}