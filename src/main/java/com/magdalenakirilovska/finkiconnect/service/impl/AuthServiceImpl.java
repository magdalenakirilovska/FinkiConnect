package com.magdalenakirilovska.finkiconnect.service.impl;

import com.magdalenakirilovska.finkiconnect.exceptions.InvalidArgumentsException;
import com.magdalenakirilovska.finkiconnect.exceptions.InvalidUserCredentialsException;
import com.magdalenakirilovska.finkiconnect.model.User;
import com.magdalenakirilovska.finkiconnect.repository.UserRepository;
import com.magdalenakirilovska.finkiconnect.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty())
            throw new InvalidArgumentsException();
        return userRepository.findByUsernameAndPassword(username, password).orElseThrow(InvalidUserCredentialsException::new);
    }
}
