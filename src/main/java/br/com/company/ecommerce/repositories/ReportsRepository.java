package br.com.company.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.models.Report;

public interface ReportsRepository extends JpaRepository<Report, Long> {

    Optional<Report> findByIdAndAccount(Long id, Account current);

}
