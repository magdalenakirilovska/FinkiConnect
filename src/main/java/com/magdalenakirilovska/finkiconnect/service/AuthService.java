package com.magdalenakirilovska.finkiconnect.service;

import com.magdalenakirilovska.finkiconnect.exceptions.InvalidArgumentsException;
import com.magdalenakirilovska.finkiconnect.exceptions.InvalidUserCredentialsException;
import com.magdalenakirilovska.finkiconnect.model.User;

public interface AuthService {
    User login(String username, String password) throws InvalidUserCredentialsException, InvalidArgumentsException;
}
