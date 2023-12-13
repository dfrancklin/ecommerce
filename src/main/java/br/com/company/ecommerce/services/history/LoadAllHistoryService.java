package br.com.company.ecommerce.services.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.company.ecommerce.models.History;

public interface LoadAllHistoryService {

    Page<History> loadAll(Pageable pageable);

}
