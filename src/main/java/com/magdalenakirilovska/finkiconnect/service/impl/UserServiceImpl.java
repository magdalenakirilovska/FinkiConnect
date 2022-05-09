package com.magdalenakirilovska.finkiconnect.service.impl;

import com.magdalenakirilovska.finkiconnect.exceptions.InvalidArgumentsException;
import com.magdalenakirilovska.finkiconnect.exceptions.PasswordsDoNotMatchException;
import com.magdalenakirilovska.finkiconnect.exceptions.UsernameAlreadyExistsException;
import com.magdalenakirilovska.finkiconnect.model.Role;
import com.magdalenakirilovska.finkiconnect.model.User;
import com.magdalenakirilovska.finkiconnect.repository.UserRepository;
import com.magdalenakirilovska.finkiconnect.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, Role role) throws InvalidArgumentsException {
        if(username==null || username.isEmpty() || password==null || password.isEmpty())
            throw new InvalidArgumentsException();
        if(!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(userRepository.findByUsername(username).isPresent() || !userRepository.findByUsername(username).isEmpty())
            throw new UsernameAlreadyExistsException(username);

        User user = new User(username, passwordEncoder.encode(password), role);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }
}
