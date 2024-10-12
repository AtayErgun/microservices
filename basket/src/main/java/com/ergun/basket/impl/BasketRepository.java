package com.ergun.basket.impl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository <Basket,Integer> {
    Basket findBasketByCustomerIdAndStatusEquals(int customer , int status);

    Basket findBasketByBasketId(int basketId);



}
