package org.example.Services.Impl;

import org.example.Dtos.ForAdd.AddIngredientDto;
import org.example.Models.Entites.Ingredient;
import org.example.Models.Exceptions.IngredientNotFoundException;
import org.example.Repositories.IngredientRepository;
import org.example.Services.IngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final ModelMapper mapper;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, ModelMapper mapper) {
        this.ingredientRepository = ingredientRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "ingredients", allEntries = true)
    public void addIngredient(AddIngredientDto addIngredientDto) {
        ingredientRepository.save(mapper.map(addIngredientDto, Ingredient.class));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "ingredients", allEntries = true)
    public void deleteIngredient(String name) {
        if (!ingredientRepository.existsByName(name))
            throw new IngredientNotFoundException("Ингредиент \"" + name + "\" не найден");
        ingredientRepository.deleteByName(name);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "ingredients", allEntries = true)
    public Ingredient forSetIngredient(String name) {
        return ingredientRepository.findByName(name)
                .orElseGet(() -> ingredientRepository.save(new Ingredient(name)));
    }
}