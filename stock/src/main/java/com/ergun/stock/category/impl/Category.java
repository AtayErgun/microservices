package com.ergun.stock.category.impl;

import com.ergun.stock.product.impl.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int categoryId;
    private String name;
    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)
    private List<Product> productList;
}

