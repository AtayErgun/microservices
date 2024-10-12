package com.ergun.basket.impl.basketitem;


import com.ergun.basket.api.basketitem.BasketItemDto;
import com.ergun.basket.api.basketitem.BasketItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketItemServiceImpl implements BasketItemService {
    private final BasketItemRepository repository;

    public BasketItem findBasketItemByBasketIdAndProductId(int basketId, int productId) {
        return repository.findBasketItemByBasket_BasketIdAndProductId(basketId, productId);
    }

    public BasketItem save(BasketItem basketItem) {
        return repository.save(basketItem);
    }

    public void delete(int basketItemId) {
        BasketItem basketItem = repository.findById(basketItemId).get();
        repository.delete(basketItem);
    }


    public BasketItemDto toDto(BasketItem basketItem) {
        return BasketItemDto.builder()
                .basketItemId(basketItem.getBasketItemId())
                .basketItemAmount(basketItem.getBasketItemAmount())
                .count(basketItem.getCount())
                .productId(basketItem.getProductId())
                .build();
    }

}
