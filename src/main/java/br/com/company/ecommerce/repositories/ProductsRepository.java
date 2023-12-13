package br.com.company.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.company.ecommerce.models.Platform;
import br.com.company.ecommerce.models.Product;

public interface ProductsRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByPlatform(Platform platform, Pageable pageable);

    Optional<Product> findByIdAndPlatform(Long productId, Platform platform);

}
