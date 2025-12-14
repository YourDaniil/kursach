package org.example.Models.Entites;

import jakarta.persistence.*;
import org.example.Models.Enums.RecipeDifficult;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe_difficult")
public class Difficult extends BaseEntity{
    private RecipeDifficult recipeDifficult;
    private String translateRecipeDifficult;

    @OneToMany(mappedBy = "difficult")
    List<Recipe> recipes = new ArrayList<>();

    public Difficult(RecipeDifficult recipeDifficult) {
        setRecipeDifficult(recipeDifficult);
        setTranslateRecipeDifficult(translateRecipeDifficult);
    }

    public Difficult() {}

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
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
        if (getRecipeDifficult().equals(RecipeDifficult.EASY)) this.translateRecipeDifficult = "Легко";
        else if (getRecipeDifficult().equals(RecipeDifficult.MIDDLE)) this.translateRecipeDifficult = "Средне";
        else if (getRecipeDifficult().equals(RecipeDifficult.CHIEF)) this.translateRecipeDifficult = "Шеф-повар";
    }
}
