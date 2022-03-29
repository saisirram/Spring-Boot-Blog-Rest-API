package com.sai.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// get JWT(token) from http request
		String token = getJWTfromToken(request);

		// validate the token
		if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
			// retrieve username from token
			String username = tokenProvider.getUsernameFromToken(token);

			// load user associated with token
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			// set this data to spring Security
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}

		filterChain.doFilter(request, response);
	}

	// generally a request contains token in format of Bearer<accessToken> so we
	// need to extract access token from it
	private String getJWTfromToken(HttpServletRequest request) {

		String BearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(BearerToken) && BearerToken.startsWith("Bearer ")) {
			return BearerToken.substring(7, BearerToken.length());
		}
		return null;

	}

}
