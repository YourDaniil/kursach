package org.example.Services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.example.Dtos.ForAdd.AddUserDto;
import org.example.Dtos.ForShow.ShowInfoUserDto;
import org.example.Models.Exceptions.UserNotFoundException;
import org.example.Models.Entites.User;
import org.example.Repositories.UserRepository;
import org.example.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "users", allEntries = true)
    public void addUser(AddUserDto addUserDto) {
        User user = mapper.map(addUserDto, User.class);
        userRepository.save(user);
    }

    @Override
    @Cacheable(value = "users", key = "'all'")
    public List<ShowInfoUserDto> allUsers() {
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, ShowInfoUserDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"users", "user"}, allEntries = true)
    public void deleteUser(String userName) {
        if (!userRepository.existsByUserName(userName))
            throw new UserNotFoundException("Пользователь \"" + userName + "\" не найден");
        userRepository.deleteByUserName(userName);
    }

    @Override
    @Cacheable(value = "user", key = "#userName")
    public ShowInfoUserDto findByUserName(String userName) {
        return mapper.map(userRepository.findByUserName(userName), ShowInfoUserDto.class);
    }

    @Override
    public User forSetUser(String userName) {
        return userRepository.findByUserName(userName).get();
    }
}