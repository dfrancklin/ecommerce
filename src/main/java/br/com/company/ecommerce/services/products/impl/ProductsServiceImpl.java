package br.com.company.ecommerce.services.products.impl;

import org.springframework.stereotype.Service;

import br.com.company.ecommerce.dtos.CreateProductRequest;
import br.com.company.ecommerce.models.Platform;
import br.com.company.ecommerce.models.Product;
import br.com.company.ecommerce.repositories.ProductsRepository;
import br.com.company.ecommerce.services.platforms.LoadPlatformByIdService;
import br.com.company.ecommerce.services.products.CreateProductService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements CreateProductService {

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

}
