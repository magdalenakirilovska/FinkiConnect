package com.magdalenakirilovska.finkiconnect.service;

import com.magdalenakirilovska.finkiconnect.exceptions.InvalidArgumentsException;
import com.magdalenakirilovska.finkiconnect.model.Role;
import com.magdalenakirilovska.finkiconnect.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, Role role) throws InvalidArgumentsException;
}
