package org.example.Dtos.SimpleDtos;

import lombok.Data;
import org.example.Models.Enums.RecipeDifficult;

import java.util.List;


public class SimpleDifficultDto {
    private RecipeDifficult recipeDifficult;
    private String translateRecipeDifficult;

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
}
