package br.com.company.ecommerce.services.products;

import br.com.company.ecommerce.models.Product;

public interface LoadProductByIdService {

	Product loadById(Long platformId, Long productId);

}
