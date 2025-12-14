package org.example.Services.Impl;

import lombok.RequiredArgsConstructor;
import org.example.Dtos.ForAdd.UserRegistrationDto;
import org.example.Models.Entites.User;
import org.example.Models.Enums.UserRoles;
import org.example.Repositories.RoleRepository;
import org.example.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(UserRegistrationDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Пароли не совпадают");
        }
        if (userRepository.findByUserName(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Логин занят");
        }

        var role = roleRepository.findByUserRole(UserRoles.USER);

        User user = new User(
                dto.getUsername(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                role
        );

        userRepository.save(user);
    }
}