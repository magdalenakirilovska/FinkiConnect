package com.magdalenakirilovska.finkiconnect.service;

import com.magdalenakirilovska.finkiconnect.model.Article;
import com.magdalenakirilovska.finkiconnect.model.ArticleFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> listAllArticles();
    void createArticle(String title, String content, MultipartFile articleFile, String username) throws IOException;
    void edit(Long id, String title, String content, MultipartFile articleFile) throws IOException;
    void deleteById(Long id);
    Optional<Article> findById(Long id);

}
