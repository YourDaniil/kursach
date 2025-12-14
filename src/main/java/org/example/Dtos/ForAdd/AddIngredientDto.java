package org.example.Dtos.ForAdd;

import org.example.Utils.Validation.Ingredient.UniqueName;

public class AddIngredientDto {
    @UniqueName
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
