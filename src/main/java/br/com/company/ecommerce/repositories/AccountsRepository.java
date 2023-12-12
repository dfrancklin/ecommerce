package br.com.company.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.company.ecommerce.models.Account;

public interface AccountsRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByEmail(String email);

}
