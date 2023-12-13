package br.com.company.ecommerce.services.platforms.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.company.ecommerce.dtos.CreatePlatformRequest;
import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.models.Platform;
import br.com.company.ecommerce.repositories.PlatformsRepository;
import br.com.company.ecommerce.services.platforms.CreatePlatformService;
import br.com.company.ecommerce.services.platforms.LoadAllPlatformsService;
import br.com.company.ecommerce.services.platforms.LoadPlatformByIdService;
import br.com.company.ecommerce.utils.CurrentAccount;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlatformsServiceImpl implements CreatePlatformService, LoadAllPlatformsService, LoadPlatformByIdService {

    private final PlatformsRepository repository;

    @Override
    public Platform create(CreatePlatformRequest request) {
        Platform platform = Platform.builder()
                .name(request.getName())
                .build();

        return repository.save(platform);
    }

    @Override
    public Page<Platform> loadAll(Pageable pageable) {
        Account current = CurrentAccount.get();

        return repository.findAllByAccount(current, pageable);
    }

    @Override
    public Platform loadById(Long id) {
        Account current = CurrentAccount.get();

        return repository.findByIdAndAccount(id, current)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find platform with provided id"));
    }

}