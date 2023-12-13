package br.com.company.ecommerce.services.accounts.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.company.ecommerce.annotations.RecordHistory;
import br.com.company.ecommerce.dtos.CreateAccountRequest;
import br.com.company.ecommerce.dtos.UpdateAccountRequest;
import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.repositories.AccountsRepository;
import br.com.company.ecommerce.services.accounts.CreateAccountService;
import br.com.company.ecommerce.services.accounts.LoadAccountByIdService;
import br.com.company.ecommerce.services.accounts.UpdateAccountService;
import br.com.company.ecommerce.utils.CurrentAccount;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements CreateAccountService, UpdateAccountService, LoadAccountByIdService {

	private final AccountsRepository repository;

	private final PasswordEncoder passwordEncoder;

	@Override
	@RecordHistory
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
	@RecordHistory
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

	@Override
	public Account loadById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Unable to find account with provided id"));
	}

}
