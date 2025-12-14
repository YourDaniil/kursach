package org.example.Dtos.ForShow;

import lombok.Data;
import org.example.Dtos.SimpleDtos.SimpleUserDto;
import org.example.Models.Entites.User;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;

public class ShowInfoCommentDto {
    private String comment;
    private SimpleUserDto user;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public SimpleUserDto getUser() {
        return user;
    }

    public void setUser(SimpleUserDto user) {
        this.user = user;
    }
}
