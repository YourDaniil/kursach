package org.example.Dtos.ForAdd;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.example.Models.Entites.User;

public class AddCommentDto {
    private String comment;

    private User user;

    @NotEmpty(message = "text is empty!")
    @Size(min = 5, max = 100, message = "5 <= text <= 100!")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
