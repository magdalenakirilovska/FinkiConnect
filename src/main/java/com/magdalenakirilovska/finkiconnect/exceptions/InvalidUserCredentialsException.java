package com.magdalenakirilovska.finkiconnect.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException() {
        super("Invalid user");
    }
}
