package br.com.company.ecommerce.services.accounts.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.company.ecommerce.dtos.CreateAccountRequest;
import br.com.company.ecommerce.dtos.UpdateAccountRequest;
import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.repositories.AccountsRepository;
import br.com.company.ecommerce.services.accounts.CreateAccountService;
import br.com.company.ecommerce.services.accounts.UpdateAccountService;
import br.com.company.ecommerce.utils.CurrentAccount;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements CreateAccountService, UpdateAccountService {

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

	@Override
	public Account update(UpdateAccountRequest request) {
		Account current = CurrentAccount.get();

		if (StringUtils.hasText(request.getName())) {
			current.setName(request.getName());
		}

		if (StringUtils.hasText(request.getEmail())) {
			current.setEmail(request.getEmail());
		}

		if (StringUtils.hasText(request.getPassword())) {
			String encodedPassword = passwordEncoder.encode(request.getPassword());
			current.setPassword(encodedPassword);
		}

		return repository.save(current);
	}

}
