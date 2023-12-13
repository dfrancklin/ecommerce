package br.com.company.ecommerce.services.platforms.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.company.ecommerce.annotations.RecordHistory;
import br.com.company.ecommerce.dtos.CreatePlatformRequest;
import br.com.company.ecommerce.dtos.UpdatePlatformRequest;
import br.com.company.ecommerce.models.Account;
import br.com.company.ecommerce.models.Platform;
import br.com.company.ecommerce.repositories.PlatformsRepository;
import br.com.company.ecommerce.services.platforms.CreatePlatformService;
import br.com.company.ecommerce.services.platforms.DeletePlatformService;
import br.com.company.ecommerce.services.platforms.LoadAllPlatformsService;
import br.com.company.ecommerce.services.platforms.LoadPlatformByIdService;
import br.com.company.ecommerce.services.platforms.UpdatePlatformService;
import br.com.company.ecommerce.utils.CurrentAccount;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlatformsServiceImpl implements CreatePlatformService, LoadAllPlatformsService, LoadPlatformByIdService,
        UpdatePlatformService, DeletePlatformService {

    private final PlatformsRepository repository;

    @Override
    @RecordHistory
    public Platform create(CreatePlatformRequest request) {
        Account current = CurrentAccount.get();
        Platform platform = Platform.builder()
                .name(request.getName())
                .account(current)
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

    @Override
    @RecordHistory
    public Platform update(Long id, UpdatePlatformRequest request) {
        Platform platform = loadById(id);

        if (StringUtils.hasText(request.getName())) {
            platform.setName(request.getName());
        }

        if (request.getEnabled() != null) {
            platform.setEnabled(request.getEnabled());
        }

        return repository.save(platform);
    }

    @Override
    @RecordHistory
    public void delete(Long id) {
        Platform platform = loadById(id);

        repository.delete(platform);
    }

}