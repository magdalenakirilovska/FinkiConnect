package com.magdalenakirilovska.finkiconnect.service.impl;

import com.magdalenakirilovska.finkiconnect.exceptions.ArticleNotFoundException;
import com.magdalenakirilovska.finkiconnect.model.Article;
import com.magdalenakirilovska.finkiconnect.model.ArticlesPerUser;
import com.magdalenakirilovska.finkiconnect.model.User;
import com.magdalenakirilovska.finkiconnect.repository.ArticlesPerUserRepository;
import com.magdalenakirilovska.finkiconnect.repository.UserRepository;
import com.magdalenakirilovska.finkiconnect.service.ArticlePerUserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticlePerUserServiceImpl implements ArticlePerUserService {

    private final ArticlesPerUserRepository articlesPerUserRepository;
    private final UserRepository userRepository;

    public ArticlePerUserServiceImpl(ArticlesPerUserRepository articlesPerUserRepository, UserRepository userRepository) {
        this.articlesPerUserRepository = articlesPerUserRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public List<Article> listAllArticles(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return articlesPerUserRepository.findByUser(user).get().getArticles();
    }

    @Transactional
    @Override
    public ArticlesPerUser addArticle(String username, Article article) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        ArticlesPerUser articlesPerUser = null;
        if(articlesPerUserRepository.findByUser(user).isPresent()){
            articlesPerUser = articlesPerUserRepository.findByUser(user).get();
        } else{
            articlesPerUser = new ArticlesPerUser(user);
        }
        articlesPerUser.addArticle(article);
        return articlesPerUserRepository.save(articlesPerUser);
    }

    @Transactional
    @Override
    public void editArticle(String username, Article article) {
        List<Article> articles = listAllArticles(username);
        Article p = articles.stream().filter(i -> i.getTitle().equals(article.getTitle())).findFirst().orElseThrow(() -> new ArticleNotFoundException(article.getTitle()));
        ArticlesPerUser articlesPerUser = articlesPerUserRepository.findByUserUsername(username).orElseThrow(() -> new ArticleNotFoundException(article.getTitle()));

        articlesPerUser.removeArticle(p);
        articlesPerUser.addArticle(article);
    }

    @Transactional
    @Override
    public void deleteArticle(String username, Article article) {
        ArticlesPerUser articlesPerUser = articlesPerUserRepository.findByUserUsername(username).orElseThrow(() -> new ArticleNotFoundException(article.getTitle()));
        articlesPerUser.removeArticle(article);
    }
}
