package org.example.Services.Impl;

import org.example.Models.Entites.Difficult;
import org.example.Models.Enums.RecipeDifficult;
import org.example.Repositories.DifficultRepository;
import org.example.Services.DifficultService;
import org.springframework.stereotype.Service;

@Service
public class DifficultServiceImpl implements DifficultService {
    private final DifficultRepository difficultRepository;

    public DifficultServiceImpl(DifficultRepository difficultRepository) {
        this.difficultRepository = difficultRepository;
    }

    @Override
    public Difficult translateDifficult(String difficultType) {
        if (difficultType.equals("Легко")) return difficultRepository.findByRecipeDifficult(RecipeDifficult.EASY);
        else if (difficultType.equals("Средне")) return difficultRepository.findByRecipeDifficult(RecipeDifficult.MIDDLE);
        else if (difficultType.equals("Шеф-повар")) return difficultRepository.findByRecipeDifficult(RecipeDifficult.CHIEF);
        else return null;
    }
}
