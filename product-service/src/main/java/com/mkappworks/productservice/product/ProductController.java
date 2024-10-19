package com.mkappworks.productservice.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);

        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(@RequestBody List<ProductPurchaseRequest> productPurchaseRequests) {
        List<Product> products = productMapper.toProductsFromProductPurchaseRequests(productPurchaseRequests);

        List<Product> dbProducts = productService.purchaseProducts(products);

        return ResponseEntity.ok(productMapper.toProductPurchaseResponses(dbProducts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable Integer id) {
        Product product = productService.findProductById(id);

        return ResponseEntity.ok(productMapper.toProductResponse(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProducts() {
        List<Product> products = productService.findAllProducts();

        return ResponseEntity.ok(productMapper.toProductResponses(products));
    }

}

