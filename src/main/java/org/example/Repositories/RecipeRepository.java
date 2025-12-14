package org.example.Repositories;

import org.example.Models.Entites.Difficult;
import org.example.Models.Entites.Recipe;
import org.example.Models.Entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, String> {
    Optional<Recipe> findByTitle(String title);

    boolean existsByTitle(String title);

    @Query(value = "SELECT * FROM recipes ORDER BY RANDOM() LIMIT 1",
            nativeQuery = true)
    Recipe findRandom();

    @Query(value = "SELECT * FROM recipes ORDER BY RANDOM() LIMIT 5",
            nativeQuery = true)
    List<Recipe> findFiveRandom();

    @Query(value = "SELECT COUNT(*) FROM recipes",
            nativeQuery = true)
    int countAllRecipes();

    List<Recipe> findByCookTimeBetween(int begin, int end);

    List<Recipe> findByDifficult(Difficult difficult);

    List<Recipe> findByMaster(User master);

    List<Recipe> findByWhoLikeIt_UserName(String userName);

    @Modifying
    @Transactional
    void deleteByTitle(String title);
}
