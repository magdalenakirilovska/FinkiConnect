package com.magdalenakirilovska.finkiconnect.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ArticlesPerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Article> articles;

    public ArticlesPerUser(User user) {
        this.user = user;
        this.articles = new ArrayList<>();
    }

    public ArticlesPerUser() {
    }

    public void addArticle(Article article){
        this.articles.add(article);
    }

    public void removeArticle(Article article){
        this.articles.remove(article);
    }
}
