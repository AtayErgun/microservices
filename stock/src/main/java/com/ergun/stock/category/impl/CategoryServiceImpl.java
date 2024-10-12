package com.ergun.stock.category.impl;


import com.ergun.stock.category.api.CategoryDto;
import com.ergun.stock.category.api.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;


    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = toEntity(categoryDto);
        category = repository.save(category);
        return toDto(category);
    }

    @Override
    public CategoryDto getCategory(String id) {
        Category category = repository.findById(Integer.parseInt(id)).get();
        return toDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        // Amele yöntemi
//        List<Category> categoryList = repository.findAll();
//        List<CategoryDto> categoryDtos = new ArrayList<>();
//        categoryList.forEach(category -> categoryDtos.add(toDto(category)));

        return repository.findAll().stream().map(this::toDto).toList();

    }


    @Override
    public CategoryDto update(CategoryDto categoryDto, String id) {
        Category category = repository.findById(Integer.parseInt(id)).get();
        category.setName(categoryDto.getName());
        category = repository.save(category);
        return toDto(category);
    }

    @Override
    public void delete(String id) {
        Category category = repository.findById(Integer.parseInt(id)).get();
        repository.delete(category);
    }


    // Repository'e gitmeden önce çalışan  metod
    public Category toEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }

    // Respons'a gitmeden önce çalışan metod
    public CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .build();

    }


    public Category getCategoryEntity(String id) {
        return repository.findById(Integer.parseInt(id)).get();
    }


}
