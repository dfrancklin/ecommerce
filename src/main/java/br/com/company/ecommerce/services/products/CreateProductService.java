package br.com.company.ecommerce.services.products;

import br.com.company.ecommerce.dtos.CreateProductRequest;
import br.com.company.ecommerce.models.Product;

public interface CreateProductService {

	Product create(Long platformId, CreateProductRequest request);

}
