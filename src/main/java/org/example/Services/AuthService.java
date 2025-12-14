package org.example.Services;

import org.example.Dtos.ForAdd.UserRegistrationDto;

public interface AuthService {
    void register(UserRegistrationDto dto);
}
