package com.ergun.stock.category.web;


import com.ergun.stock.category.api.CategoryDto;
import com.ergun.stock.category.api.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest categoryRequest) {
        return toResponse(categoryService.save(categoryRequest.toDto()));
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable(value = "id") String id) {
        return toResponse(categoryService.getCategory(id));
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable(value = "id") String id, @RequestBody CategoryRequest categoryRequest) {
        return toResponse(categoryService.update(categoryRequest.toDto(), id));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") String id) {
        try {
            categoryService.delete(id);
            return "Başarıyla silindi";
        } catch (Exception ex) {
            return "Silme işleminde şu hata oldu: " + ex.getMessage();
        }
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategory()
                .stream()
                .map(categoryDto -> toResponse(categoryDto))
                .collect(Collectors.toList());
    }

    // Client'a gitmeden önce çalışacak metod
    public CategoryResponse toResponse(CategoryDto categoryDto) {
        return CategoryResponse.builder()
                .categoryId(categoryDto.getCategoryId())
                .name(categoryDto.getName())
                .code(200)
                .message("Başarılı")
                .build();
    }
}
