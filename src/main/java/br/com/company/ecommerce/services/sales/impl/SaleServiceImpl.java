package br.com.company.ecommerce.services.sales.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.company.ecommerce.dtos.CreateSaleItemRequest;
import br.com.company.ecommerce.dtos.CreateSaleRequest;
import br.com.company.ecommerce.models.Platform;
import br.com.company.ecommerce.models.Product;
import br.com.company.ecommerce.models.Sale;
import br.com.company.ecommerce.models.SaleItem;
import br.com.company.ecommerce.repositories.ProductsRepository;
import br.com.company.ecommerce.repositories.SalesRepository;
import br.com.company.ecommerce.services.platforms.LoadPlatformByIdService;
import br.com.company.ecommerce.services.sales.CreateSaleService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements CreateSaleService {

    private final LoadPlatformByIdService loadPlatformByIdService;

    private final SalesRepository repository;

    private final ProductsRepository productsRepository;

    @Override
    public Sale create(Long platformId, CreateSaleRequest request) {
        Platform platform = loadPlatformByIdService.loadById(platformId);

        List<Long> productIds = request.getItems()
                .stream()
                .map(CreateSaleItemRequest::getProductId)
                .toList();

        Map<Long, Product> products = productsRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<SaleItem> items = request.getItems()
                .stream()
                .map(item -> {
                    Product product = products.get(item.getProductId());

                    if (product == null) {
                        return null;
                    }

                    return SaleItem.builder()
                            .amount(item.getAmount())
                            .soldPrice(product.getPrice())
                            .product(product)
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();

        Sale sale = Sale.builder()
                .platform(platform)
                .items(items)
                .build();

        return repository.save(sale);
    }

}
