package br.com.company.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.company.ecommerce.dtos.AuthResponse;
import br.com.company.ecommerce.dtos.CreateAccountRequest;
import br.com.company.ecommerce.dtos.Credentials;
import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.security.JwtManager;
import br.com.company.ecommerce.services.accounts.CreateAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {

	private final CreateAccountService createAccountService;

	private final AuthenticationManager authenticationManager;

	private final JwtManager jwtManager;

	@PostMapping("/sign-up")
	@ResponseStatus(HttpStatus.CREATED)
	public AuthResponse signUp(@Valid @RequestBody CreateAccountRequest request) {
		Account account = createAccountService.create(request);
		String token = jwtManager.createToken(account);

		return AuthResponse.builder().accessToken(token).build();
	}

	@PostMapping("/sign-in")
	public AuthResponse signIn(@Valid @RequestBody Credentials request) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				request.getEmail(), request.getPassword());
		Authentication authenticated = authenticationManager.authenticate(authentication);
		Account account = (Account) authenticated.getPrincipal();
		String token = jwtManager.createToken(account);

		return AuthResponse.builder().accessToken(token).build();
	}

}
