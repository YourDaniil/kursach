package org.example.Dtos.SimpleDtos;

import lombok.Data;

public class SimpleCommentDto {
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
