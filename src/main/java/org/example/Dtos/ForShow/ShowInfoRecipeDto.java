package org.example.Dtos.ForShow;

import lombok.Data;
import org.example.Dtos.SimpleDtos.*;
import org.example.Models.Entites.*;

import java.util.List;

public class ShowInfoRecipeDto {
    private String title;
    private String instructions;
    private String description;
    private int cookTime;
    private SimpleDifficultDto difficult;
    private String imageRoot;
    private double avgRating;
    private int servings;
    private SimpleUserDto master;
    private SimpleCategoryDto category;
    private List<SimpleCommentDto> comments;
    private List<SimpleIngredientDto> ingredients;
    private List<SimpleUserDto> whoLikeIt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public SimpleDifficultDto getDifficult() {
        return difficult;
    }

    public void setDifficult(SimpleDifficultDto difficult) {
        this.difficult = difficult;
    }

    public String getImageRoot() {
        return imageRoot;
    }

    public void setImageRoot(String imageRoot) {
        this.imageRoot = imageRoot;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public SimpleUserDto getMaster() {
        return master;
    }

    public void setMaster(SimpleUserDto master) {
        this.master = master;
    }

    public SimpleCategoryDto getCategory() {
        return category;
    }

    public void setCategory(SimpleCategoryDto category) {
        this.category = category;
    }

    public List<SimpleCommentDto> getComments() {
        return comments;
    }

    public void setComments(List<SimpleCommentDto> comments) {
        this.comments = comments;
    }

    public List<SimpleIngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<SimpleIngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    public List<SimpleUserDto> getWhoLikeIt() {
        return whoLikeIt;
    }

    public void setWhoLikeIt(List<SimpleUserDto> whoLikeIt) {
        this.whoLikeIt = whoLikeIt;
    }

    @Override
    public String toString() {
        return "ShowInfoRecipeDto{" +
                "title='" + title + '\'' +
                ", instructions='" + instructions + '\'' +
                ", description='" + description + '\'' +
                ", cookTime=" + cookTime +
                ", difficult=" + difficult +
                ", imageRoot='" + imageRoot + '\'' +
                ", avgRating=" + avgRating +
                ", servings=" + servings +
                ", master=" + master +
                ", category='" + category + '\'' +
                ", comments=" + comments +
                ", ingredients=" + ingredients +
                ", whoLikeIt=" + whoLikeIt +
                '}';
    }
}
