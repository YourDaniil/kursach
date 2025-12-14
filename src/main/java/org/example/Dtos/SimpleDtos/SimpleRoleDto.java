package org.example.Dtos.SimpleDtos;

import lombok.Data;
import org.example.Models.Enums.UserRoles;

import java.util.List;

public class SimpleRoleDto {
    private UserRoles userRoles;

    public UserRoles getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
    }
}
