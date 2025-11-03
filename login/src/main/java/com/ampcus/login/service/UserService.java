package com.ampcus.login.service;

import com.ampcus.login.dto.SearchUserDto;
import com.ampcus.login.entity.User;

public interface UserService {

    public SearchUserDto searchUser(User user);
}
