package org.example.Services.Impl;

import org.example.Dtos.ForShow.ShowInfoUserDto;
import org.example.Models.Entites.User;
import org.example.Models.Enums.UserRoles;
import org.example.Repositories.UserRepository;
import org.example.Services.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public AdminServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Cacheable(value = "users", key = "'admin_list'")
    public List<ShowInfoUserDto> findAllUsers() {
        List<User> users = userRepository.findAll().stream().filter(user ->
                user.getRole().getUserRoles().equals(UserRoles.USER)).toList();
        return users.stream()
                .map(user -> mapper.map(user, ShowInfoUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"users", "user"}, allEntries = true)
    public void toggleBan(String userId) {
        User user = userRepository.findById(userId).get();
        user.setBanned(!user.isBanned());
        userRepository.save(user);
    }
}