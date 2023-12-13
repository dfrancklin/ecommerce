package br.com.company.ecommerce.controllers;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.company.ecommerce.dtos.CreatePlatformRequest;
import br.com.company.ecommerce.dtos.CreateProductRequest;
import br.com.company.ecommerce.dtos.UpdatePlatformRequest;
import br.com.company.ecommerce.models.Platform;
import br.com.company.ecommerce.models.Product;
import br.com.company.ecommerce.services.platforms.CreatePlatformService;
import br.com.company.ecommerce.services.platforms.DeletePlatformService;
import br.com.company.ecommerce.services.platforms.LoadAllPlatformsService;
import br.com.company.ecommerce.services.platforms.LoadPlatformByIdService;
import br.com.company.ecommerce.services.platforms.UpdatePlatformService;
import br.com.company.ecommerce.services.products.CreateProductService;
import br.com.company.ecommerce.services.products.LoadAllProductsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/platforms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PlatformsController {

    private final CreatePlatformService createPlatformService;

    private final LoadAllPlatformsService loadAllPlatformsService;

    private final LoadPlatformByIdService loadPlatformByIdService;

    private final UpdatePlatformService updatePlatformService;

    private final DeletePlatformService deletePlatformService;

    private final CreateProductService createProductService;

    private final LoadAllProductsService loadAllProductsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Platform createPlatform(@Valid @RequestBody CreatePlatformRequest request) {
        return createPlatformService.create(request);
    }

    @GetMapping
    public Page<Platform> getAllPlatforms(@ParameterObject Pageable pageable) {
        return loadAllPlatformsService.loadAll(pageable);
    }

    @GetMapping("/{id}")
    public Platform getAllPlatforms(@PathVariable Long id) {
        return loadPlatformByIdService.loadById(id);
    }

    @PutMapping("/{id}")
    public Platform updatePlatform(@PathVariable Long id, @Valid @RequestBody UpdatePlatformRequest request) {
        return updatePlatformService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlatform(@PathVariable Long id) {
        deletePlatformService.delete(id);
    }

    @PostMapping("/{platformId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProductOnPlatform(@PathVariable Long platformId,
            @Valid @RequestBody CreateProductRequest request) {
        return createProductService.create(platformId, request);
    }

    @GetMapping("/{platformId}/products")
    public Page<Product> getAllProductsFromPlatform(@PathVariable Long platformId, @ParameterObject Pageable pageable) {
        return loadAllProductsService.loadAll(platformId, pageable);
    }

}
