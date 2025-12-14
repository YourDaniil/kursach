package org.example.Services;

import org.example.Dtos.ForAdd.AddIngredientDto;
import org.example.Models.Entites.Ingredient;

public interface IngredientService {
    void addIngredient(AddIngredientDto addIngredientDto);
    void deleteIngredient(String name);
    Ingredient forSetIngredient(String name);
}
