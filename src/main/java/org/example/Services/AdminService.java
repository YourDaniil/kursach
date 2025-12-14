package org.example.Services;

import org.example.Dtos.ForShow.ShowInfoUserDto;

import java.util.List;

public interface AdminService {
    List<ShowInfoUserDto> findAllUsers();
    void toggleBan(String userId);
}
