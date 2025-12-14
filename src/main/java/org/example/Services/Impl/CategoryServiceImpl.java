package org.example.Services.Impl;

import org.example.Dtos.ForAdd.AddCategoryDto;
import org.example.Dtos.ForShow.ShowInfoCategoryDto;
import org.example.Models.Entites.Category;
import org.example.Models.Exceptions.CategoryNotFoundException;
import org.example.Repositories.CategoryRepository;
import org.example.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    public final CategoryRepository categoryRepository;
    public final ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper){
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "categories", allEntries = true)
    public void addCategory(AddCategoryDto addCategory) {
        categoryRepository.save(mapper.map(addCategory, Category.class));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "categories", allEntries = true)
    public void deleteCategory(String name) {
        if (!categoryRepository.existsByName(name))
            throw new CategoryNotFoundException("Категория \"" + name + "\" не найдена");
        categoryRepository.deleteByName(name);
    }

    @Override
    @Cacheable(value = "categories", key = "'random_five'")
    public List<ShowInfoCategoryDto> findFiveRandom() {
        return categoryRepository.findFiveRandom().stream().map(category ->
                mapper.map(category, ShowInfoCategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public Category forSetCategory(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));
    }

    @Override
    @Cacheable(value = "categories", key = "'all'")
    public List<ShowInfoCategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(category ->
                mapper.map(category, ShowInfoCategoryDto.class)).collect(Collectors.toList());
    }
}