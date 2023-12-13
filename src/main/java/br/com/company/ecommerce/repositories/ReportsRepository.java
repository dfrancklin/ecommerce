package br.com.company.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.company.ecommerce.models.Report;

public interface ReportsRepository extends JpaRepository<Report, Long> {
}
