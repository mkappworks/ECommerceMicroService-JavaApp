package com.mkappworks.ecommerce.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductClient productClient;

    public List<PurchaseResponse> getProducts(List<PurchaseRequest> purchaseRequests) {
        //purchase the products -> product-ms using RestTemplate
        return productClient.purchaseProducts(purchaseRequests);
    }
}
