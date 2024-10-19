package com.mkappworks.productservice.product;

import com.mkappworks.productservice.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Integer createProduct(Product product) {
        return productRepository.save(product)
                .getId();
    }

    public List<Product> purchaseProducts(List<Product> products) {
        var productIds = products
                .stream()
                .map(Product::getId)
                .toList();

        var storedProducts = productRepository.findAllByIdInOrderById(productIds);

        if (products.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exits");
        }

        var sortedRequestProducts = products
                .stream()
                .sorted(Comparator.comparing(Product::getId))
                .toList();

        var purchasedProducts = new ArrayList<Product>();
        for(int i = 0; i < storedProducts.size(); i++) {
            var storedProduct = storedProducts.get(i);
            var requestProduct = sortedRequestProducts.get(i);

            if(storedProduct.getAvailableQuantity() < requestProduct.getAvailableQuantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with Id: " + requestProduct.getId());
            }

            var newAvailableQuantity = storedProduct.getAvailableQuantity() - requestProduct.getAvailableQuantity();
            storedProduct.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(storedProduct);
            purchasedProducts.add(storedProduct);
        }

        return purchasedProducts;
    }

    public Product findProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with the id: " + id));
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
