package com.ampcus.login.service;

import com.ampcus.login.dto.searchUserDto;
import com.ampcus.login.entity.User;

public interface UserService {

    public searchUserDto searchUser(User user);
}
