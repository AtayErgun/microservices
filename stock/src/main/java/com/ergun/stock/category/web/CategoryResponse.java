package com.ergun.stock.category.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    private final int code;
    private final String message;
    private final int categoryId;
    private final String name;
// List of Product Dto olması lazım! Yanlış olmuş
// İhitayaça göre iki tane response döneceksin!
}
