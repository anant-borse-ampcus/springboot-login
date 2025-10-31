package com.ampcus.login.service.impl;

import com.ampcus.login.dto.searchUserDto;
import com.ampcus.login.entity.User;
import com.ampcus.login.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public searchUserDto searchUser(User user) {
       return new searchUserDto(
               user.getEmail(),
               user.getRole().toString(),
               user.isEnabled()
       );
    }
    public List<searchUserDto> toDtoList(List<User> users) {
        return users.stream().map(this::searchUser).toList();
    }
}
