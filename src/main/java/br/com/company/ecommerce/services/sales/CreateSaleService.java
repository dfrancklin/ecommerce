package br.com.company.ecommerce.services.sales;

import br.com.company.ecommerce.dtos.CreateSaleRequest;
import br.com.company.ecommerce.models.Sale;

public interface CreateSaleService {

	Sale create(Long platformId, CreateSaleRequest request);

}
