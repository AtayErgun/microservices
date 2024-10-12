package com.ergun.stock.product.impl;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
//    List<Product> findAllByCategory_CategoryId(int categoryId);
}
