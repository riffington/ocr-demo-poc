package com.ocr.demo.config;

import static com.ocr.demo.utils.Constants.AUTH_HEADER;
import static com.ocr.demo.utils.Constants.BEARER_PREFIX;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ocr.demo.repository.UserRepositoryImpl;
import com.ocr.demo.utils.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.core.instrument.util.StringUtils;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private UserRepositoryImpl userRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.cookie.name}")
	private String cookieName;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String username = null;
		String jwtTokenValue = getJwtFromCookie(request);
		
		if (StringUtils.isBlank(jwtTokenValue)) {
			jwtTokenValue = getJwtFromAuthHeader(request);
		}
		
		if (StringUtils.isNotBlank(jwtTokenValue)) {
			try {
				username = jwtTokenUtil.getUsername(jwtTokenValue);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token Info");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		}

		// validate token
		if (StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userRepository.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set authentication
			if (jwtTokenUtil.validateToken(jwtTokenValue, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		chain.doFilter(request, response);
	}
	
	public String getJwtFromAuthHeader(HttpServletRequest request) {
		String jwtTokenValue = null;
		
		jwtTokenValue = request.getHeader(AUTH_HEADER);
		
		if (StringUtils.isNotBlank(jwtTokenValue) && jwtTokenValue.startsWith(BEARER_PREFIX)) {
			// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
			jwtTokenValue = jwtTokenValue.substring(BEARER_PREFIX.length());
		}
		
		return jwtTokenValue;
	}

	private String getJwtFromCookie(HttpServletRequest request) {
		String jwtTokenValue = null;
		
		if (request.getCookies() != null && request.getCookies().length > 0) {
			final Optional<Cookie> jwtCookie = Arrays.stream(request.getCookies()).filter(c -> cookieName.equals(c.getName())).findFirst();
			
			if (jwtCookie.isPresent()) {
				jwtTokenValue = jwtCookie.get().getValue();
			}
		}
		
		return jwtTokenValue;
	}
}