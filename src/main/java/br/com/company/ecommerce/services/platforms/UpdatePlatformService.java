package br.com.company.ecommerce.services.platforms;

import br.com.company.ecommerce.dtos.UpdatePlatformRequest;
import br.com.company.ecommerce.models.Platform;

public interface UpdatePlatformService {

	Platform update(Long id, UpdatePlatformRequest request);

}
