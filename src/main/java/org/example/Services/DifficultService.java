package org.example.Services;

import org.example.Models.Entites.Difficult;
import org.springframework.stereotype.Service;

public interface DifficultService {
    Difficult translateDifficult(String difficultStr);
}
