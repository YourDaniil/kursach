package org.example.Repositories;

import org.example.Models.Entites.Category;
import org.example.Models.Entites.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByName(String name);

    boolean existsByName(String name);

    @Query(value = "SELECT * FROM category ORDER BY RANDOM() LIMIT 5",
            nativeQuery = true)
    List<Category> findFiveRandom();

    @Modifying
    @Transactional
    void deleteByName(String name);
}
