package com.magdalenakirilovska.finkiconnect.exceptions;

public class ArticleFileNotFoundException extends RuntimeException {
    public ArticleFileNotFoundException(Long id) {
        super("Article file with id " + id + " cannot be found.");
    }
}
