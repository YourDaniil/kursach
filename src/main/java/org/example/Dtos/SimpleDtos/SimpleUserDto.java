package org.example.Dtos.SimpleDtos;

import lombok.Data;
// Если ShowInfoRoleDto простой (без списков юзеров внутри), можно оставить его.
// Если в нем тоже есть список List<ShowInfoUserDto>, замени на String roleName или SimpleRoleDto.
import org.example.Dtos.ForShow.ShowInfoRoleDto;

import java.util.List;

public class SimpleUserDto {
    private String id;
    private String userName;
    private String email;
    private boolean isBanned;
    private ShowInfoRoleDto role;

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

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public ShowInfoRoleDto getRole() {
        return role;
    }

    public void setRole(ShowInfoRoleDto role) {
        this.role = role;
    }

}