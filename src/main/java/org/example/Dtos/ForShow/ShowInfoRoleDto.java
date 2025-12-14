package org.example.Dtos.ForShow;

import lombok.Data;
import org.example.Dtos.SimpleDtos.SimpleUserDto;
import org.example.Models.Entites.User;
import org.example.Models.Enums.UserRoles;

import java.util.ArrayList;
import java.util.List;

public class ShowInfoRoleDto {
    private UserRoles userRole;
    private List<SimpleUserDto> users = new ArrayList<>();

    public UserRoles getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }

    public List<SimpleUserDto> getUsers() {
        return users;
    }

    public void setUsers(List<SimpleUserDto> users) {
        this.users = users;
    }
}
