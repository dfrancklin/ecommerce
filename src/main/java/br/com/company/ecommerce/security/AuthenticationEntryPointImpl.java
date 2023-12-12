package br.com.company.ecommerce.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String path = request.getRequestURI();

		log.error("Unable to process the request at path \"{}\". Error: {}", path, exception.getMessage(), exception);
		log.debug("Error stack:", exception);

		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

}
