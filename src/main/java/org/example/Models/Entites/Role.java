package org.example.Models.Entites;

import jakarta.persistence.*;
import org.example.Models.Enums.UserRoles;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{
    private UserRoles userRole;

    @OneToMany(mappedBy = "role")
    List<User> users = new ArrayList<>();

    public Role(UserRoles userRoles) {
        setUserRoles(userRoles);
    }

    public Role() {}

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    public UserRoles getUserRoles() {
        return userRole;
    }

    public void setUserRoles(UserRoles userRoles) {
        this.userRole = userRoles;
    }
}
