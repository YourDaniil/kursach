package org.example.Dtos.ForShow;

import lombok.Data;
import org.example.Dtos.SimpleDtos.SimpleCommentDto;
import org.example.Dtos.SimpleDtos.SimpleRecipeDto;
import org.example.Dtos.SimpleDtos.SimpleRoleDto;
import org.example.Models.Entites.Comment;
import org.example.Models.Entites.Recipe;
import org.example.Models.Entites.Role;

import java.util.List;

public class ShowInfoUserDto {
    private String id;
    private String userName;
    private String email;
    private boolean isBanned;
    private SimpleRoleDto role;
    private List<SimpleRecipeDto> userCreateRecipes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SimpleRoleDto getRole() {
        return role;
    }

    public void setRole(SimpleRoleDto role) {
        this.role = role;
    }

    public List<SimpleRecipeDto> getUserCreateRecipe() {
        return userCreateRecipes;
    }

    public void setUserCreateRecipe(List<SimpleRecipeDto> userCreateRecipes) {
        this.userCreateRecipes = userCreateRecipes;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
