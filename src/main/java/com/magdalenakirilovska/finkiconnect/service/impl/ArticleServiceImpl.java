package com.magdalenakirilovska.finkiconnect.service.impl;

import com.magdalenakirilovska.finkiconnect.exceptions.ArticleNotFoundException;
import com.magdalenakirilovska.finkiconnect.model.Article;
import com.magdalenakirilovska.finkiconnect.model.ArticleFile;
import com.magdalenakirilovska.finkiconnect.repository.ArticleRepository;
import com.magdalenakirilovska.finkiconnect.service.ArticleService;
import com.magdalenakirilovska.finkiconnect.service.ArticlePerUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticlePerUserService articlePerUserService;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticlePerUserService articlePerUserService) {
        this.articleRepository = articleRepository;
        this.articlePerUserService = articlePerUserService;
    }

    @Transactional
    public List<Article> listAllArticles() {
        return articleRepository.findAll();
    }

    public void createArticle(String title, String content, MultipartFile file, String username) throws IOException {
        ArticleFile articleFile = null;
        if (file != null && !file.isEmpty()) {
            articleFile = new ArticleFile();
            articleFile.setName(StringUtils.cleanPath(file.getOriginalFilename()));
            articleFile.setContentType(file.getContentType());
            articleFile.setData(file.getBytes());
            articleFile.setSize(file.getSize());
        }

        Article article = new Article(title, content, articleFile, username);
        articleRepository.save(article);
        articlePerUserService.addArticle(username, article);
    }

    @Override
    public void edit(Long id, String title, String content, MultipartFile file) throws IOException {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id.toString()));
        article.setTitle(title);
        article.setContent(content);
        articlePerUserService.editArticle(article.getUsername(), article);
        articleRepository.deleteById(article.getId());
        articleRepository.save(article);
    }

    @Override
    public void deleteById(Long id) {
        Article article = articleRepository.findById(id).get();
        articlePerUserService.deleteArticle(article.getUsername(), article);
        articleRepository.deleteById(id);
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

}