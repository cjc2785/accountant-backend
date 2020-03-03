package com.gcit.accountant.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.gcit.accountant.exception.BadJwtException;
import com.gcit.accountant.model.UserPrincipal;
import com.gcit.accountant.service.UserPrincipalService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserPrincipalService principalService;
	
	// Ensure the filter is not called on /api/login
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return request.getServletPath().startsWith("/api/login");
	}
	

	// Called on every route except /api/login
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {		

		Optional<String> optJwt = getJwtFromRequest(request);

		try {

			if (optJwt.isPresent()) {
				
				String jwt = optJwt.get();
				
				tokenProvider.validateToken(jwt);
				Integer userId = tokenProvider.getUserIdFromJwt(jwt);

				UserPrincipal principal = principalService.findById(userId);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						principal, null, principal.getAuthorities());

				authentication.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
						);

				SecurityContextHolder.getContext()
					.setAuthentication(authentication);
			}
			
			filterChain.doFilter(request, response);
			
		} catch (BadJwtException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token");
		}
	}

	private Optional<String> getJwtFromRequest(HttpServletRequest request) {

		Cookie cookie = WebUtils.getCookie(request, "session-id");

		if (cookie != null) {
			return Optional.of(cookie.getValue());
		}
		return Optional.empty();
	}
}
