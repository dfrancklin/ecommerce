package br.com.company.ecommerce.services.products.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.company.ecommerce.dtos.CreateProductRequest;
import br.com.company.ecommerce.dtos.UpdateProductRequest;
import br.com.company.ecommerce.models.Platform;
import br.com.company.ecommerce.models.Product;
import br.com.company.ecommerce.repositories.ProductsRepository;
import br.com.company.ecommerce.services.platforms.LoadPlatformByIdService;
import br.com.company.ecommerce.services.products.CreateProductService;
import br.com.company.ecommerce.services.products.DeleteProductService;
import br.com.company.ecommerce.services.products.LoadAllProductsService;
import br.com.company.ecommerce.services.products.LoadProductByIdService;
import br.com.company.ecommerce.services.products.UpdateProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements CreateProductService, LoadAllProductsService, LoadProductByIdService,
        UpdateProductService, DeleteProductService {

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

    @Override
    public Product loadById(Long platformId, Long productId) {
        Platform platform = loadPlatformByIdService.loadById(platformId);

        return repository.findByIdAndPlatform(productId, platform)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find product with provided id"));
    }

    @Override
    public Product update(Long platformId, Long productId, UpdateProductRequest request) {
        Product product = loadById(platformId, productId);

        if (StringUtils.hasText(request.getName())) {
            product.setName(request.getName());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        return repository.save(product);
    }

    @Override
    public void delete(Long platformId, Long productId) {
        Product product = loadById(platformId, productId);

        repository.delete(product);
    }

}
