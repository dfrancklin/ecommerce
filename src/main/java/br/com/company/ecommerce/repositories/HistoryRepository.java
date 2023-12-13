package br.com.company.ecommerce.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.models.History;

public interface HistoryRepository extends JpaRepository<History, Long> {

    Page<History> findAllByAccount(Account current, Pageable pageable);

}
