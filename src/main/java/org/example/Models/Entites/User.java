package org.example.Models.Entites;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "is_banned", nullable = false)
    private boolean isBanned = false; // по умолчанию false

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "master", fetch = FetchType.EAGER)
    private List<Recipe> userCreateRecipes;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    public User() {}

    public User(String userName, String email,
                String password, Role role) {
        setUserName(userName);
        setEmail(email);
        setPassword(password);
        setRole(role);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Recipe> getUserCreateRecipes() {
        return userCreateRecipes;
    }

    public void setUserCreateRecipes(List<Recipe> userCreateRecipes) {
        this.userCreateRecipes = userCreateRecipes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
