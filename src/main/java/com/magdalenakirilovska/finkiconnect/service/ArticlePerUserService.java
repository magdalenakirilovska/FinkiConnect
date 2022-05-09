package com.magdalenakirilovska.finkiconnect.service;

import com.magdalenakirilovska.finkiconnect.model.Article;
import com.magdalenakirilovska.finkiconnect.model.ArticlesPerUser;

import java.util.List;

public interface ArticlePerUserService {
    List<Article> listAllArticles(String username);
    ArticlesPerUser addArticle(String username, Article article);
    void editArticle(String username, Article article);
    void deleteArticle(String username, Article article);
}
