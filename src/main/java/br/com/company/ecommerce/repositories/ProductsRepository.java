package br.com.company.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.company.ecommerce.models.Product;

public interface ProductsRepository extends JpaRepository<Product, Long> {
}
