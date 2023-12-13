package br.com.company.ecommerce.services.products.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.company.ecommerce.dtos.CreateProductRequest;
import br.com.company.ecommerce.models.Platform;
import br.com.company.ecommerce.models.Product;
import br.com.company.ecommerce.repositories.ProductsRepository;
import br.com.company.ecommerce.services.platforms.LoadPlatformByIdService;
import br.com.company.ecommerce.services.products.CreateProductService;
import br.com.company.ecommerce.services.products.LoadAllProductsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements CreateProductService, LoadAllProductsService {

    private final LoadPlatformByIdService loadPlatformByIdService;

    private final ProductsRepository repository;

    @Override
    public Product create(Long platformId, CreateProductRequest request) {
        Platform platform = loadPlatformByIdService.loadById(platformId);

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .platform(platform)
                .build();

        return repository.save(product);
    }

    @Override
    public Page<Product> loadAll(Long platformId, Pageable pageable) {
        Platform platform = loadPlatformByIdService.loadById(platformId);

        return repository.findAllByPlatform(platform, pageable);
    }

}
