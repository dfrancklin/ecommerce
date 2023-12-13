package br.com.company.ecommerce.services.platforms.impl;

import org.springframework.stereotype.Service;

import br.com.company.ecommerce.dtos.CreatePlatformRequest;
import br.com.company.ecommerce.models.Platform;
import br.com.company.ecommerce.repositories.PlatformsRepository;
import br.com.company.ecommerce.services.platforms.CreatePlatformService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlatformsServiceImpl implements CreatePlatformService {

    private final PlatformsRepository repository;

    @Override
    public Platform create(CreatePlatformRequest request) {
        Platform platform = Platform.builder()
                .name(request.getName())
                .build();

        return repository.save(platform);
    }

}