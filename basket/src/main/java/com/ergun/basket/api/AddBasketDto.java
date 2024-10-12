package com.ergun.basket.api;

import com.ergun.basket.api.basketitem.BasketItemDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class AddBasketDto {
    private final int productId;
    private final int customerId;
    private final int count;
}
