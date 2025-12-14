package org.example.Models.Entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;

    private Ingredient() {}

    public Ingredient(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
