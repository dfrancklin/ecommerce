package br.com.company.ecommerce.services.accounts.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.company.ecommerce.dtos.CreateAccountRequest;
import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.repositories.AccountsRepository;
import br.com.company.ecommerce.services.accounts.CreateAccountService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements CreateAccountService {

	private final AccountsRepository repository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public Account create(CreateAccountRequest request) {
		String encodedPassword = passwordEncoder.encode(request.getPassword());

		Account account = Account.builder()
				.name(request.getName())
				.email(request.getEmail())
				.password(encodedPassword)
				.build();

		return repository.save(account);
	}

}
