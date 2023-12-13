package br.com.company.ecommerce.services.platforms;

import br.com.company.ecommerce.dtos.CreatePlatformRequest;
import br.com.company.ecommerce.models.Platform;

public interface CreatePlatformService {

	Platform create(CreatePlatformRequest request);

}
