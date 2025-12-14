package org.example.Dtos.ForShow;

import org.example.Dtos.SimpleDtos.SimpleRecipeDto;
import org.example.Models.Enums.RecipeDifficult;

import java.util.ArrayList;
import java.util.List;


public class ShowInfoDifficultDto {
    private RecipeDifficult recipeDifficult;
    private String translateRecipeDifficult;
    private List<SimpleRecipeDto> recipes = new ArrayList<>();

    public RecipeDifficult getRecipeDifficult() {
        return recipeDifficult;
    }

    public void setRecipeDifficult(RecipeDifficult recipeDifficult) {
        this.recipeDifficult = recipeDifficult;
    }

    public String getTranslateRecipeDifficult() {
        return translateRecipeDifficult;
    }

    public void setTranslateRecipeDifficult(String translateRecipeDifficult) {
        this.translateRecipeDifficult = translateRecipeDifficult;
    }

    public List<SimpleRecipeDto> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<SimpleRecipeDto> recipes) {
        this.recipes = recipes;
    }
}
