package br.com.company.ecommerce.services.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.company.ecommerce.models.Product;

public interface LoadAllProductsService {

	Page<Product> loadAll(Long platformId, Pageable pageable);

}
