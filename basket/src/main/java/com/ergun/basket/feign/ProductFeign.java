package com.ergun.basket.feign;

import com.ergun.basket.api.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stock",url = "https://localhost:8081")
public interface ProductFeign {
    @GetMapping("/products/{id}")
    ProductDto get(@PathVariable(value = "id")String id);
}
