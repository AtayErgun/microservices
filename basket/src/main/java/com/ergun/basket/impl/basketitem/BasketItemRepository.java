package com.ergun.basket.impl.basketitem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketItemRepository extends JpaRepository<BasketItem , Integer> {
     BasketItem findBasketItemByBasket_BasketIdAndProductId(int basketId , int productId);

}
