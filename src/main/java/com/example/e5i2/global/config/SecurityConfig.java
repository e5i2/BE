package com.example.e5i2.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. CORS 설정을 가장 먼저 적용합니다.
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth
                // 2. [핵심] Preflight(OPTIONS) 요청은 보안 필터를 거치지 않고 무조건 허용합니다.
                // 이 설정이 없으면 OPTIONS 요청에 대해 302 리다이렉트가 발생할 수 있습니다.
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 1. [중요] Credentials가 true일 때는 패턴을 사용하는 것이 안전합니다.
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));

        // 2. [중요] OPTIONS 메서드를 명시적으로 포함합니다.
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // 3. 브라우저가 보낼 수 있는 헤더를 구체적으로 지정하거나 모두 허용합니다.
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "x-requested-with", "Cache-Control"));
        configuration.setExposedHeaders(Collections.singletonList("*"));

        // 4. [중요] 인증 정보(쿠키, 헤더 등)를 허용합니다.
        // 프론트에서 credentials: 'include' 옵션을 쓸 경우 필수입니다.
        configuration.setAllowCredentials(true);

        // 5. 예비 요청 결과를 브라우저에 캐싱하여 성능을 높입니다.
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}