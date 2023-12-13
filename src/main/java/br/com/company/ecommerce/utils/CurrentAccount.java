package br.com.company.ecommerce.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.company.ecommerce.models.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrentAccount {

	public static Account get() {
		return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
