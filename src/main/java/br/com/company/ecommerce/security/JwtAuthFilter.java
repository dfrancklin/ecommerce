package br.com.company.ecommerce.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtManager jwtManager;

	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String headerValue = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (!StringUtils.hasText(headerValue)) {
			filterChain.doFilter(request, response);

			return;
		}

		String path = request.getRequestURI();

		try {
			if (!headerValue.startsWith("Bearer ")) {
				throw new AuthenticationCredentialsNotFoundException("Authorization Header not found");
			}

			String[] tokenParts = headerValue.split(" ");

			if (tokenParts.length != 2) {
				throw new AuthenticationCredentialsNotFoundException("Malformed Authorization Header");
			}

			String accessToken = tokenParts[1];

			if (!jwtManager.isValid(accessToken)) {
				throw new BadCredentialsException("Invalid Authorization Header");
			}

			String username = jwtManager.getEmail(accessToken);
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (!userDetails.isEnabled()) {
				throw new DisabledException("User is disabled");
			}

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			log.error("Unable to validate authorization at path \"{}\". Error: {}.", path, e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		}
	}

}
