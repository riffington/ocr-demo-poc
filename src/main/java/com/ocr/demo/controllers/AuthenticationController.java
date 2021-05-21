package com.ocr.demo.controllers;

import static com.ocr.demo.utils.Constants.AUTHENTICATION_SUCCESSFUL;
import static com.ocr.demo.utils.Constants.INVALID_CREDENTIALS;
import static com.ocr.demo.utils.Constants.SIGNOUT_SUCCESSFUL;
import static com.ocr.demo.utils.Constants.USER_DISABLED;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ocr.demo.domain.JwtRequest;
import com.ocr.demo.domain.SimpleResponse;
import com.ocr.demo.domain.UserInfo;
import com.ocr.demo.services.MainServiceImpl;
import com.ocr.demo.utils.JwtTokenUtil;	

@Controller
@CrossOrigin
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private MainServiceImpl mainService;

	@Value("${jwt.cookie.name}")
	private String cookieName;

	@Value("${jwt.default.expiry}")
	private int defaultExpiry;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(
			@RequestBody JwtRequest authenticationRequest,
			HttpServletResponse response) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserInfo userInfo = mainService.getUserInfoByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userInfo);
		
		addJwtToResponseCookie(token, response, defaultExpiry);

		SimpleResponse authResp = new SimpleResponse(AUTHENTICATION_SUCCESSFUL);
		
	    return new ResponseEntity<>(authResp, HttpStatus.OK);
	}

	// NOTE this does not actually prevent the token from being able to be used
	// It will override the cookie with an expired one (in case the app is using that, but using the old token
	// in a Authz header will still be valid until the token expires (unless we write something to save and "revoke" it)
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public ResponseEntity<?> signout(HttpServletResponse response) throws Exception {

		// override the old cookie on the response header
		addJwtToResponseCookie(null, response, 0);

		SimpleResponse authResp = new SimpleResponse(SIGNOUT_SUCCESSFUL);
		
	    return new ResponseEntity<>(authResp, HttpStatus.OK);
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception(USER_DISABLED, e);
		} catch (BadCredentialsException e) {
			throw new Exception(INVALID_CREDENTIALS, e);
		}
	}
	
	private void addJwtToResponseCookie(String jwt, HttpServletResponse response, int expiry) {
		// create a cookie
		Cookie cookie = new Cookie(cookieName, jwt);
		cookie.setMaxAge(expiry);
		cookie.setSecure(false); // false for local development
		cookie.setHttpOnly(false); // false for local development
		response.addCookie(cookie);
	}
}