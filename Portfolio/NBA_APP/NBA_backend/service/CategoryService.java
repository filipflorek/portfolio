package com.florek.NBA_backend.service;


import com.florek.NBA_backend.model.entries.Category;
import com.florek.NBA_backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(Category category){
        categoryRepository.save(category);
    }
    
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(int id){
        return categoryRepository.findById(id);
    }

    public void deleteCategory(int id){
        if(categoryRepository.findById(id).isPresent()){
            categoryRepository.deleteById(id);
        }
    }

    public void editCategory(int id, Category newData){
        categoryRepository.findById(id)
                .map(category -> {
                    category.setCategory(newData.getCategory());
                    return categoryRepository.save(category);
                });
    }
}
