package org.example.Dtos.ForAdd;

import org.example.Utils.Validation.Category.UniqueCategory;

public class AddCategoryDto {
    @UniqueCategory
    String name;

    public String getCategory() {
        return name;
    }

    public void setCategory(String name) {
        this.name = name;
    }
}
