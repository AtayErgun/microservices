package com.ergun.stock.product.web;


import com.ergun.stock.product.api.ProductDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    private final String name;
    private final int stock;
    private final double price;
    private final int categoryId;

    public ProductDto toDto(){
        return ProductDto.builder()
                .name(this.name)
                .stock(this.stock)
                .price(this.price)
                .categoryId(this.categoryId)
                .build();
    }
}
