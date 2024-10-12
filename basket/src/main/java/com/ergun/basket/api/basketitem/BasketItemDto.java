package com.ergun.basket.api.basketitem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasketItemDto {
    private int basketItemId;
    private double basketItemAmount;
    private int count;
    private final int productId;
}
