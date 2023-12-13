package br.com.company.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.company.ecommerce.models.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
