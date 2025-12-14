package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.Models.Entites.*;
import org.example.Models.Enums.RecipeDifficult;
import org.example.Models.Enums.UserRoles;
import org.example.Repositories.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class Init implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final IngredientRepository ingredientRepository;
    private final DifficultRepository difficultRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;


    public Init(UserRepository userRepository, RoleRepository roleRepository,
                RecipeRepository recipeRepository, CategoryRepository categoryRepository,
                CommentRepository commentRepository, IngredientRepository ingredientRepository,
                DifficultRepository difficultRepository, PasswordEncoder passwordEncoder,
                @Value("${app.default.password}") String defaultPassword) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.ingredientRepository = ingredientRepository;
        this.difficultRepository = difficultRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
        log.info("init компонент инициализирован");
    }

    @Override
    public void run(String... args) {
        log.info("Запуск инициализации начальных данных...");
        initRoles();
        initUsers();
        initCategory();
        initComments();
        initIngredients();
        initDifficult();
        initRecipes();
        log.info("Инициализация начальных данных завершена успешно");
    }

    public void initRoles() {
        if (roleRepository.count() == 0) {
            log.info("Создание базовых ролей...");
            roleRepository.saveAll(List.of(
                    new Role(UserRoles.USER),
                    new Role(UserRoles.ADMIN)
            ));
            log.info("Роли созданы: USER, ADMIN");
        }
    }

    public void initUsers() {
        if (userRepository.count() == 0) {
            log.info("Создание тестовых пользователей...");

            userRepository.saveAll(List.of(
                    new User("Daniil", "123@email", passwordEncoder.encode("password"), roleRepository.findByUserRole(UserRoles.USER)),
                    new User("Dima", "123@email1", passwordEncoder.encode("password1"), roleRepository.findByUserRole(UserRoles.USER)),
                    new User("user", "123@email2", passwordEncoder.encode("password2"), roleRepository.findByUserRole(UserRoles.USER)),
                    new User("123123", "123@123", passwordEncoder.encode("123123"), roleRepository.findByUserRole(UserRoles.USER)),
                    new User("admin", "admin@admin", passwordEncoder.encode(defaultPassword), roleRepository.findByUserRole(UserRoles.ADMIN))
            ));
        }
    }

    public void initCategory() {
        if (categoryRepository.count() == 0) {
            log.info("Создание категорий...");

            categoryRepository.saveAll(List.of(
                    new Category("Суп"),
                    new Category("Сладость"),
                    new Category("Мучное"),
                    new Category("Напиток"),
                    new Category("Мясо"),
                    new Category("Макаронные изделия")
            ));
        }
    }

    public void initComments() {
        if (commentRepository.count() == 0) {
            log.info("Создание комментариев...");

            commentRepository.saveAll(List.of(
                    new Comment("comment super text", userRepository.findByUserName("Daniil").get()),
                    new Comment("comment puper text", userRepository.findByUserName("Daniil").get()),
                    new Comment("comment super-puper text", userRepository.findByUserName("Dima").get())
            ));
        }
    }

    public void initIngredients() {
        if (ingredientRepository.count() == 0) {
            log.info("Создание ингредиентов...");

            ingredientRepository.saveAll(List.of(
                    new Ingredient("Вода - 2 л"),
                    new Ingredient("Соль"),
                    new Ingredient("Сахар"),
                    new Ingredient("Картоха - 0,5 кг"),
                    new Ingredient("Свекла - 2 шт"),
                    new Ingredient("Капуста - 1 шт"),
                    new Ingredient("Вода"),
                    new Ingredient("Сливки - 0,15 л"),
                    new Ingredient("Яйца - 3 шт"),
                    new Ingredient("Вода - 0,5 л"),
                    new Ingredient("Молоко - 0,5 л"),
                    new Ingredient("Яйца - 2 шт"),
                    new Ingredient("Сушеное яблоко - 2 шт"),
                    new Ingredient("Сухофрукты - 0,5 кг"),
                    new Ingredient("Свинина - 2 кг"),
                    new Ingredient("Специи"),
                    new Ingredient("Маринад - 2 л"),
                    new Ingredient("Вода - 1 л"),
                    new Ingredient("Спагетти - 0,5 кг"),
                    new Ingredient("Вода - 0,25 л"),
                    new Ingredient("Тесто - 2,5 кг"),
                    new Ingredient("Изюм"),
                    new Ingredient("Яйца - 4 шт")
            ));
        }
    }

    public void initDifficult() {
        if (difficultRepository.count() == 0) {
            log.info("Создание уровней сложности...");

            difficultRepository.saveAll(List.of(
                    new Difficult(RecipeDifficult.EASY),
                    new Difficult(RecipeDifficult.MIDDLE),
                    new Difficult(RecipeDifficult.CHIEF)
            ));
        }
    }

    public void initRecipes(){
        if (recipeRepository.count() == 0) {
            log.info("Создание рецептов...");

            List<Ingredient> ingredients1 = Arrays.asList(
                    ingredientRepository.findByName("Вода - 2 л").get(),
                    ingredientRepository.findByName("Соль").get(),
                    ingredientRepository.findByName("Сахар").get(),
                    ingredientRepository.findByName("Картоха - 0,5 кг").get(),
                    ingredientRepository.findByName("Свекла - 2 шт").get(),
                    ingredientRepository.findByName("Капуста - 1 шт").get()
            );

            List<Ingredient> ingredients2 = Arrays.asList(
                    ingredientRepository.findByName("Вода").get(),
                    ingredientRepository.findByName("Соль").get(),
                    ingredientRepository.findByName("Сахар").get(),
                    ingredientRepository.findByName("Сливки - 0,15 л").get(),
                    ingredientRepository.findByName("Яйца - 3 шт").get()
            );

            List<Ingredient> ingredients3 = Arrays.asList(
                    ingredientRepository.findByName("Вода - 0,5 л").get(),
                    ingredientRepository.findByName("Соль").get(),
                    ingredientRepository.findByName("Сахар").get(),
                    ingredientRepository.findByName("Молоко - 0,5 л").get(),
                    ingredientRepository.findByName("Яйца - 2 шт").get()
            );

            List<Ingredient> ingredients4 = Arrays.asList(
                    ingredientRepository.findByName("Вода").get(),
                    ingredientRepository.findByName("Сахар").get(),
                    ingredientRepository.findByName("Сушеное яблоко - 2 шт").get(),
                    ingredientRepository.findByName("Сухофрукты - 0,5 кг").get()
            );

            List<Ingredient> ingredients5 = Arrays.asList(
                    ingredientRepository.findByName("Свинина - 2 кг").get(),
                    ingredientRepository.findByName("Специи").get(),
                    ingredientRepository.findByName("Маринад - 2 л").get()
            );

            List<Ingredient> ingredients6 = Arrays.asList(
                    ingredientRepository.findByName("Вода - 1 л").get(),
                    ingredientRepository.findByName("Спагетти - 0,5 кг").get()
            );

            List<Ingredient> ingredients7 = Arrays.asList(
                    ingredientRepository.findByName("Вода - 0,25 л").get(),
                    ingredientRepository.findByName("Тесто - 2,5 кг").get(),
                    ingredientRepository.findByName("Изюм").get(),
                    ingredientRepository.findByName("Яйца - 4 шт").get()
            );

            List<User> whoLikeIt = Arrays.asList(
                    userRepository.findByUserName("Daniil").get()
            );
            List<User> noOneLikeIt = new ArrayList<>();

            recipeRepository.saveAll(List.of(

                    new Recipe("Борщ",
                            "Инструкция борщу",
                            "Описание борщу",
                            45,
                            difficultRepository.findByRecipeDifficult(RecipeDifficult.EASY),
                            "/pic/food/борщ.png",
                            9.8,
                            10,
                            categoryRepository.findByName("Суп").get(),
                            null,
                            ingredients1,
                            null,
                            whoLikeIt),

                    new Recipe("Бизе",
                            "Инструкция бизе",
                            "Описание бизе",
                            100,
                            difficultRepository.findByRecipeDifficult(RecipeDifficult.EASY),
                            "/pic/food/бизе.png",
                            10,
                            15,
                            categoryRepository.findByName("Сладость").get(),
                            Arrays.asList(
                                    commentRepository.findByComment("comment super text").get(),
                                    commentRepository.findByComment("comment puper text").get()
                            ),
                            ingredients2,
                            userRepository.findByUserName("Daniil").get(),
                            whoLikeIt),

                    new Recipe("Блины",
                            "Инструкция блинов",
                            "Описание блинов",
                            10,
                            difficultRepository.findByRecipeDifficult(RecipeDifficult.EASY),
                            "/pic/food/блины.png",
                            10,
                            15,
                            categoryRepository.findByName("Мучное").get(),
                            Arrays.asList(commentRepository.findByComment("comment super-puper text").get()),
                            ingredients3,
                            null,
                            noOneLikeIt),

                    new Recipe("Компот",
                            "Инструкция компота",
                            "Описание компота",
                            10,
                            difficultRepository.findByRecipeDifficult(RecipeDifficult.MIDDLE),
                            "/pic/food/компот.png",
                            10,
                            15,
                            categoryRepository.findByName("Напиток").get(),
                            null,
                            ingredients4,
                            null,
                            noOneLikeIt),

                    new Recipe("Шашлык",
                            "Инструкция шашлыка",
                            "Описание шашлыка",
                            10,
                            difficultRepository.findByRecipeDifficult(RecipeDifficult.MIDDLE),
                            "/pic/food/шашлык.png",
                            10,
                            15,
                            categoryRepository.findByName("Мясо").get(),
                            null,
                            ingredients5,
                            null,
                            noOneLikeIt),

                    new Recipe("Спагетти",
                            "Инструкция спагетти",
                            "Описание спагетти",
                            10,
                            difficultRepository.findByRecipeDifficult(RecipeDifficult.MIDDLE),
                            "/pic/food/спагетти.png",
                            10,
                            15,
                            categoryRepository.findByName("Макаронные изделия").get(),
                            null,
                            ingredients6,
                            null,
                            noOneLikeIt),

                    new Recipe("Улитка",
                            "Инструкция улитка",
                            "Описание улитка",
                            10,
                            difficultRepository.findByRecipeDifficult(RecipeDifficult.CHIEF),
                            "/pic/food/улитка.png",
                            10,
                            15,
                            categoryRepository.findByName("Мучное").get(),
                            null,
                            ingredients7,
                            null,
                            noOneLikeIt)
            ));
        }
    }
}
