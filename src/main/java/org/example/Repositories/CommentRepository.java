package org.example.Repositories;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.Models.Entites.Comment;
import org.example.Models.Entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    Optional<Comment> findByComment(String comment);
    Optional<Comment> findByUser(User user);

    boolean existsById(String id);

    @Modifying
    @Transactional
    void deleteById(String id);
}
