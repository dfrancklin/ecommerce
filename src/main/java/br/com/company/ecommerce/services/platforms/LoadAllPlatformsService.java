package br.com.company.ecommerce.services.platforms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.company.ecommerce.models.Platform;

public interface LoadAllPlatformsService {

	Page<Platform> loadAll(Pageable pageable);

}
