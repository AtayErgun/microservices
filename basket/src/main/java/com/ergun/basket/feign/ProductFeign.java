package com.ergun.basket.feign;

import com.ergun.basket.api.ProductDto;
import com.ergun.stock.product.web.ProductRequest;
import com.ergun.stock.product.web.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "stock", url = "https://localhost:8081")
public interface ProductFeign {
    @GetMapping("/products/{id}")
    ProductResponse get(@PathVariable(value = "id") String id);

    @PostMapping("/products")
    ProductResponse create(@RequestBody ProductRequest request);
}
