package br.com.company.ecommerce.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.company.ecommerce.dtos.UpdateAccountRequest;
import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.services.accounts.UpdateAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProfileController {

	private final UpdateAccountService updateAccountService;

	@PutMapping
	public Account updateProfile(@Valid @RequestBody UpdateAccountRequest request) {
		return updateAccountService.update(request);
	}

}
