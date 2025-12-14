package org.example.Services;

import org.example.Dtos.ForAdd.AddCategoryDto;
import org.example.Dtos.ForShow.ShowInfoCategoryDto;
import org.example.Models.Entites.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(AddCategoryDto addCategory);
    void deleteCategory(String name);
    List<ShowInfoCategoryDto> findFiveRandom();
    Category forSetCategory(String categoryName);
    List<ShowInfoCategoryDto> findAll();
}
