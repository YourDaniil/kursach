package org.example.Models.Entites;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String instructions;

    @Column()
    private String description;

    @Column(nullable = false)
    private int cookTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "difficult_id")
    private Difficult difficult;

    @Column(nullable = false)
    private String imageRoot;

    @Column(nullable = false)
    private double avgRating;

    // количество порций
    @Column(nullable = false)
    private int servings;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "masterUser_id", nullable = true)
    private User master;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "recipe_comment",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_favouriteRecipe",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> whoLikeIt;

    private Recipe() {}

    public Recipe(String title, String instructions,
                   String description, int cookTime,
                   Difficult difficult, String imageRoot,
                   double avgRating, int servings,
                   Category category, List<Comment> comment,
                   List<Ingredient> ingredients, User master,
                   List<User> whoLikeIt) {
        setTitle(title);
        setInstructions(instructions);
        setDescription(description);
        setCookTime(cookTime);
        setDifficult(difficult);
        setImageRoot(imageRoot);
        setAvgRating(avgRating);
        setServings(servings);
        setCategory(category);
        setMaster(master);
        setComments(comment);
        setIngredients(ingredients);
        setWhoLikeIt(whoLikeIt);
    }

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


    public Difficult getDifficult() {
        return difficult;
    }

    public void setDifficult(Difficult difficult) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<User> getWhoLikeIt() {
        return whoLikeIt;
    }

    public void setWhoLikeIt(List<User> whoLikeIt) {
        this.whoLikeIt = whoLikeIt;
    }
}
