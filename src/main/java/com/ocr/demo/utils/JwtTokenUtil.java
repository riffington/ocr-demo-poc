package com.ocr.demo.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ocr.demo.domain.UserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.micrometer.core.instrument.util.StringUtils;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -28738729389L;
	private static final String ROLE = "role";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String PROFILE_IMAGE = "profileImage";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.default.expiry}")
	private int defaultExpiry;
	
	//validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		if (StringUtils.isBlank(token) || userDetails == null) {
			return null;
		}
		
		final String username = getUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	//generate token for user
	public String generateToken(UserInfo userInfo) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(ROLE, userInfo.getRole());
		claims.put(FIRST_NAME, userInfo.getFirstName());
		claims.put(LAST_NAME, userInfo.getLastName());
		claims.put(PROFILE_IMAGE, String.format("%s.%s", userInfo.getImageName(), userInfo.getImageType()));
		return doGenerateToken(claims, userInfo.getUsername());
	}
	
	public String getUsername(String token) {
		return getTokenClaim(token, Claims::getSubject);
	}

	public Date getExpirationDate(String token) {
		return getTokenClaim(token, Claims::getExpiration);
	}

	public <T> T getTokenClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims)
				.setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + defaultExpiry * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDate(token);
		return expiration.before(new Date());
	}
}