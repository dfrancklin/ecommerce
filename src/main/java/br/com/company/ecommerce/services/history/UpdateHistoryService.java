package br.com.company.ecommerce.services.history;

import br.com.company.ecommerce.dtos.UpdateHistoryRequest;
import br.com.company.ecommerce.models.History;

public interface UpdateHistoryService {

	History update(Long id, UpdateHistoryRequest request);

}
