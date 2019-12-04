package com.gcit.accountant.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gcit.accountant.exception.BadJwtException;
import com.gcit.accountant.model.UserPrincipal;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {


	@Value("${jwt.secret}")
	private String jwtSecret;


	private Long jwtExpirationInMs = 500000000L;



	public String generateToken(UserPrincipal principal) {

		int userId = principal.getUser().getUserId();
		String userIdString = Integer.toString(userId);

		Instant now = Instant.now();
		Instant expirationInstant = now.plusMillis(jwtExpirationInMs);

		Date issuedAt = Date.from(now);
		Date expirationDate = Date.from(expirationInstant);

		return Jwts.builder()
				.setSubject(userIdString)
				.setIssuedAt(issuedAt)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public Integer getUserIdFromJwt(String token) {

		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();

		return Integer.parseInt(claims.getSubject());
	}

	public boolean validateToken(String authToken) throws BadJwtException {

		try {

			Jwts.parser()
			.setSigningKey(jwtSecret)
			.parseClaimsJws(authToken);

			return true;
		} catch (SignatureException e) {
			throw new BadJwtException();
		} catch (MalformedJwtException e) {
			throw new BadJwtException();
		} catch (ExpiredJwtException e) {
			throw new BadJwtException();
		} catch (UnsupportedJwtException e) {
			throw new BadJwtException();
		} catch (IllegalArgumentException e) {
			throw new BadJwtException();
		}
	}
}