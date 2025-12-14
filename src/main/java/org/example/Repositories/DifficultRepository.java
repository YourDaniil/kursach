package org.example.Repositories;

import org.example.Models.Entites.Difficult;
import org.example.Models.Enums.RecipeDifficult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DifficultRepository extends JpaRepository<Difficult, String> {
    Difficult findByRecipeDifficult(RecipeDifficult difficult);
}
