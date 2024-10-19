package com.mkappworks.productservice.product;

import com.mkappworks.productservice.category.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .availableQuantity(request.availableQuantity())
                .price(request.price())
                .category(
                        Category.builder()
                                .id(request.categoryId())
                                .build()
                )
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .availableQuantity(product.getAvailableQuantity())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .categoryDescription(product.getCategory().getDescription())
                .build();
    }

    public List<ProductResponse> toProductResponses(List<Product> products) {
        return products
                .stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    public List<Product> toProductsFromProductPurchaseRequests(List<ProductPurchaseRequest> resquestList) {
        return resquestList
                .stream()
                .map((req) -> Product.builder()
                        .id(req.productId())
                        .availableQuantity(req.quantity())
                        .build())
                .collect(Collectors.toList());

    }

    public List<ProductPurchaseResponse> toProductPurchaseResponses(List<Product> products) {
        return products
                .stream()
                .map((product) -> ProductPurchaseResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .availableQuantity(product.getAvailableQuantity())
                        .price(product.getPrice())
                        .build())
                .collect(Collectors.toList());

    }
}
