package br.com.company.ecommerce.services.products;

import br.com.company.ecommerce.dtos.UpdateProductRequest;
import br.com.company.ecommerce.models.Product;

public interface UpdateProductService {

	Product update(Long platformId, Long productId, UpdateProductRequest request);

}
