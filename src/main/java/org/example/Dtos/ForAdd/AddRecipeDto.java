package org.example.Dtos.ForAdd;

import jakarta.validation.constraints.*;
import org.example.Models.Entites.*;
import org.example.Utils.Validation.Recipe.UniqueRecipeTitle;

import java.util.List;

public class AddRecipeDto {
    @UniqueRecipeTitle
    private String title;
    private String instructions;
    private String description;
    private int cookTime;
    private Difficult difficult;
    private String imageRoot;
    private double avgRating;
    private int servings;
    private User master;
    private List<Comment> comments;
    private Category category;
    private List<Ingredient> ingredients;
    private List<User> whoLikeIt;

    @NotNull(message = "ingredients is null!")
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NotNull(message = "category is null!")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getMaster() {
        return master;
    }
    public void setMaster(User master) {
        this.master = master;
    }

    @Min(value = 1, message = "cookTime > 0!")
    @NotNull(message = "servings is null!")
    public int getServings() {
        return servings;
    }
    public void setServings(int servings) {
        this.servings = servings;
    }

    @Min(value = 1, message = "cookTime > 0!")
    @Max(value = 6, message = "cookTime < 6!")
    @NotNull(message = "avgRating is null!")
    public double getAvgRating() {
        return avgRating;
    }
    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public String getImageRoot() {
        return imageRoot;
    }
    public void setImageRoot(String imageRoot) {
        this.imageRoot = imageRoot;
    }

    @NotNull(message = "difficult is null!")
    public Difficult getDifficult() {
        return difficult;
    }
    public void setDifficult(Difficult difficult) {
        this.difficult = difficult;
    }

    @Min(value = 1, message = "cookTime > 0!")
    @NotNull(message = "cookTime is null!")
    public int getCookTime() {
        return cookTime;
    }
    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @NotEmpty(message = "Instructions is empty!")
    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @NotEmpty(message = "Title is empty!")
    @Size(min = 2, max = 25, message = "2 <= Title <= 25!")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getWhoLikeIt() {
        return whoLikeIt;
    }
    public void setWhoLikeIt(List<User> whoLikeIt) {
        this.whoLikeIt = whoLikeIt;
    }
}
