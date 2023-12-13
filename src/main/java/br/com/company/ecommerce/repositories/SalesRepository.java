package br.com.company.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.company.ecommerce.models.Sale;

public interface SalesRepository extends JpaRepository<Sale, Long> {
}
