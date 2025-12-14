package org.example.Services;

import org.example.Dtos.ForAdd.AddUserDto;
import org.example.Dtos.ForShow.ShowInfoUserDto;
import org.example.Models.Entites.User;

import java.util.List;

public interface UserService {
    void addUser(AddUserDto addUserDto);
    List<ShowInfoUserDto> allUsers();
    void deleteUser(String userName);
    ShowInfoUserDto findByUserName(String userName);
    User forSetUser(String userName);
}
