package br.com.company.ecommerce.services.history;

import br.com.company.ecommerce.dtos.CreateHistoryRequest;
import br.com.company.ecommerce.models.History;

public interface CreateHistoryService {

    History create(CreateHistoryRequest request);

}
