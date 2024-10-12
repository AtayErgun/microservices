package com.ergun.stock.product.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private  int productId;
    private  String name;
    private  int stock;
    private  double price;
    private  int categoryId;


    public ProductDto(int productId) {
        this.productId = productId;
    }
}
