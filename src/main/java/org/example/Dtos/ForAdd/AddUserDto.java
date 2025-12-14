package org.example.Dtos.ForAdd;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.example.Models.Entites.Comment;
import org.example.Models.Entites.Recipe;
import org.example.Models.Entites.Role;
import org.example.Utils.Validation.User.UniqueEmail;
import org.example.Utils.Validation.User.UniquePassword;
import org.example.Utils.Validation.User.UniqueUserName;

import java.util.List;

public class AddUserDto {

    @UniqueUserName
    private String userName;
    @UniqueEmail
    private String email;
    @UniquePassword
    private String password;
    private Role role;
    private List<Recipe> userCreateRecipe;
    private List<Comment> comments;

    @NotEmpty(message = "UserName is empty!")
    @Size(min = 2, max = 20, message = "2 <= userName <= 20!")
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @NotEmpty(message = "Email is empty!")
    @Size(min = 15, message = "15 <= Email!")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Password is empty!")
    @Size(min = 8, message = "8 <= Password!")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Role is empty!")
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public List<Recipe> getUserCreateRecipe() {
        return userCreateRecipe;
    }

    public void setUserCreateRecipe(List<Recipe> userCreateRecipe) {
        this.userCreateRecipe = userCreateRecipe;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
