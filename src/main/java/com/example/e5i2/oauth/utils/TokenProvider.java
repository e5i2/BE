package com.example.e5i2.oauth.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.e5i2.global.properties.JwtProperties;
import com.example.e5i2.oauth.data.response.TokenResponse;
import com.example.e5i2.oauth.exception.AuthErrorCode;
import com.example.e5i2.oauth.exception.AuthException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider {
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String CLAIM_TYPE = "type";
	private static final String TYPE_ACCESS = "access";
	private static final String TYPE_REFRESH = "refresh";
	private final JwtProperties jwtProperties;
	private final Key hmacKey;

	public TokenProvider(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;

		byte[] keyBytes = jwtProperties.secret().getBytes(StandardCharsets.UTF_8);
		this.hmacKey = Keys.hmacShaKeyFor(keyBytes);
		log.info("JWT Secret Key가 로드되었습니다.");
	}

	/***
	 * 특정 Member ID에 대한 AccessToken, RefreshToken 쌍을 생성합니다
	 * @param memberId Member 고유 ID
	 * @return TokenResponse
	 */
	public TokenResponse generateToken(Long memberId) {
		Date now = new Date();
		String accessToken = createAccessToken(memberId, now);
		String refreshToken = createRefreshToken(memberId, now);

		return TokenResponse.builder()
			.accessToken(accessToken)
			.accessTokenExpiration(jwtProperties.access().expiration())
			.build();
	}

	public String createAccessToken(Long memberId, Date now) {
		Claims claims = Jwts.claims().setSubject(String.valueOf(memberId));
		claims.put(CLAIM_TYPE, TYPE_ACCESS);
		long expiration = jwtProperties.access().expiration();

		return createToken(claims, now, expiration);
	}

	public String createRefreshToken(Long memberId, Date now) {
		Claims claims = Jwts.claims().setSubject(String.valueOf(memberId));
		claims.put(CLAIM_TYPE, TYPE_REFRESH);
		long expiration = jwtProperties.refresh().expiration();

		return createToken(claims, now, expiration);
	}

	/***
	 * JWT 토큰 문자열을 생성합니다
	 * @param claims 토큰에 포함될 정보
	 * @param now 토큰 발행 시간
	 * @param expiration 토큰 유효 기간
	 * @return JWT 토큰 문자열
	 */
	private String createToken(Claims claims, Date now, long expiration) {
		Date expireDate = new Date(now.getTime() + expiration);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expireDate)
			.signWith(this.hmacKey, SignatureAlgorithm.HS256)
			.compact();
	}

	/***
	 * JWT 토큰을 추출합니다
	 * @param bearerToken bearerToken
	 * @return JWT 토큰 문자열
	 */
	public String resolveToken(String bearerToken) {
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(7);
		}

		return null;
	}

	/***
	 * 주어진 JWT 토큰을 파싱하고 검증하여 Claims를 추출합니다
	 * 토큰 검증에 실패할 경우 상황에 맞는 AuthException이 발생합니다
	 * @param token JWT 토큰 문자열
	 * @return 파싱된 Claims 객체
	 */
	private Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(this.hmacKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			throw new AuthException(AuthErrorCode.INVALID_TOKEN_SIGNATURE);
		} catch (ExpiredJwtException e) {
			throw new AuthException(AuthErrorCode.EXPIRED_TOKEN);
		} catch (UnsupportedJwtException e) {
			throw new AuthException(AuthErrorCode.INVALID_TOKEN);
		} catch (IllegalArgumentException e) {
			throw new AuthException(AuthErrorCode.INVALID_TOKEN_FORM);
		}
	}

	/***
	 * 주어진 JWT 토큰의 서명 및 유효 기간을 검증합니다
	 * @param token JWT 토큰 문자열
	 * @return 토큰이 유효하면 true 반환
	 */
	public boolean validateToken(String token) {
		parseClaims(token);
		return true;
	}

	/***
	 * 유효한 JWT 토큰으로부터 Member ID를 추출합니다
	 * @param token JWT 토큰 문자열
	 * @return Member ID
	 */
	public Long getMemberId(String token) {
		Claims claims = parseClaims(token);
		return Long.parseLong(claims.getSubject());
	}

	/***
	 * 유효한 JWT 토큰으로부터 토큰 만료 시간을 추출합니다
	 * @param token JWT 토큰 문자열
	 * @return 토큰 만료 시간
	 */
	public long getExpiration(String token) {
		Claims claims = parseClaims(token);
		return claims.getExpiration().getTime();
	}

	/***
	 * JWT Token의 타입을 확인합니다
	 * @param token JWT 토큰 문자열
	 * @return access/refresh
	 */
	public String getTokenType(String token) {
		Claims claims = parseClaims(token);
		return claims.get(CLAIM_TYPE, String.class);
	}
}
