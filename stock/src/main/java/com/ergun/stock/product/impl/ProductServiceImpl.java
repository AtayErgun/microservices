package com.ergun.stock.product.impl;

import com.ergun.stock.category.impl.CategoryServiceImpl;
import com.ergun.stock.product.api.ProductDto;
import com.ergun.stock.product.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final CategoryServiceImpl categoryService;


    @Override
    public  ProductDto save(ProductDto productDto) {
        Product product = toEntity(productDto);
        product = repository.save(product);
        return toDto(product);
    }

    @Override
    public ProductDto getProduct(String id) {
        Product product = repository.findById(Integer.parseInt(id)).get();
        product = repository.save(product);
        return  toDto(product);
    }

    @Override
    public ProductDto update(ProductDto productDto, String id) {
        Product product = repository.findById(Integer.parseInt(id)).get();
        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryService.getCategoryEntity(String.valueOf(productDto.getCategoryId())));
        product = repository.save(product);

        return toDto(product);
    }

    @Override
    public void delete(String id) {
        repository.delete(getProductEntity(id));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return repository.findAll().stream()
                .map(product -> toDto(product))
                .collect(Collectors.toList());
    }

//    public List<ProductDto> getProductsByCategoryId(String categoryId) {
//        return repository.findAllByCategory_CategoryId(Integer.parseInt(categoryId))
//                .stream()
//                .map(product -> toDto(product))
//                .collect(Collectors.toList());
//    }


    // Repository'e gitmeden önce çalışacak
    public Product toEntity(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryService.getCategoryEntity(String.valueOf(productDto.getCategoryId())));
        return product;
    }

    // Response'tan önce çalışacak metod
    public ProductDto toDto(Product product){
        return ProductDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory().getCategoryId())
                .build();
    }

    public Product getProductEntity(String id){
        Product product = repository.findById(Integer.parseInt(id)).get();
        return product;
    }

}
