package br.com.company.ecommerce.services.history;

import br.com.company.ecommerce.models.History;

public interface LoadHistoryByIdService {

	History loadById(Long id);

}
