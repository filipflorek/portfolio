package com.florek.NBA_backend.controller;


import com.florek.NBA_backend.model.entries.Category;
import com.florek.NBA_backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("nba/articles/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public  CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping
    public void createCategory(@Valid @NonNull @RequestBody Category category){
        categoryService.createCategory(category);
    }

    @DeleteMapping(path = "{id}")
    public void deleteCategory(@PathVariable("id") int categoryId){
        categoryService.deleteCategory(categoryId);
    }

    @PutMapping(path = "{id}")
    public void editCategory(@PathVariable("id") int categoryId, @Valid @NonNull @RequestBody Category newData){
        categoryService.editCategory(categoryId, newData);
    }

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping(path = "{id}")
    public Category getCategoryById(@PathVariable("id") int categoryId){
        return categoryService.getCategoryById(categoryId).orElse(null);
    }
}
