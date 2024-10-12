package com.ergun.basket.api;

import com.ergun.basket.api.basketitem.BasketItemDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BasketDto {
    private final int basketId;
    private final double totalAmount;
    private final int status;
    private final int customerId;
    private final List<BasketItemDto> basketItemList;

}
