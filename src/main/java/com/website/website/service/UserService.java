package com.website.website.service;


import com.website.website.dto.UserDto;
import com.website.website.entity.User;

public interface UserService {
    User findByUsername(String username);
    User save (UserDto userDto);
    User save (User user);
}
