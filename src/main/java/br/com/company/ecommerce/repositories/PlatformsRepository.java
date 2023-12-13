package br.com.company.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.models.Platform;

public interface PlatformsRepository extends JpaRepository<Platform, Long> {

	Page<Platform> findAllByAccount(Account account, Pageable pageable);

	Optional<Platform> findByIdAndAccount(Long id, Account account);

}
