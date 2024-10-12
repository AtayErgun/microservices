package com.ergun.basket.impl;

import com.ergun.basket.impl.basketitem.BasketItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int basketId;
    private double totalAmount;
    private int status;
    private int customerId;
    @OneToMany(mappedBy = "basket" , cascade = CascadeType.ALL)
    private List<BasketItem> basketItemList;

}
