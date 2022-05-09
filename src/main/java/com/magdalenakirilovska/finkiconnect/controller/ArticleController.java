package com.magdalenakirilovska.finkiconnect.controller;

import com.magdalenakirilovska.finkiconnect.exceptions.ArticleNotFoundException;
import com.magdalenakirilovska.finkiconnect.model.Article;
import com.magdalenakirilovska.finkiconnect.service.ArticleService;
import com.magdalenakirilovska.finkiconnect.service.ArticlePerUserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticlePerUserService articlePerUserService;

    public ArticleController(ArticleService articleService, ArticlePerUserService articlePerUserService) {
        this.articleService = articleService;
        this.articlePerUserService = articlePerUserService;
    }

    @GetMapping
    public String showAllArticles(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Article> articles = articleService.listAllArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("bodyContent", "articles");
        return "master-template";
    }

    @GetMapping("/{username}")
    public String getArticlesPerUserPage(@PathVariable String username, Model model) {
        model.addAttribute("articles", articlePerUserService.listAllArticles(username));
        model.addAttribute("bodyContent", "articles-per-user");
        return "master-template";
    }

    @GetMapping("/add-article")
    public String addArticlePage(Model model) {
        model.addAttribute("bodyContent", "add-article");
        return "master-template";
    }

    @PostMapping("/add")
    public String createArticle(@RequestParam(required = false) Long id,
                                @RequestParam String title,
                                @RequestParam String mytextarea,
                                @RequestParam("file") MultipartFile file,
                                HttpServletRequest request) throws IOException {
        if (id != null) {
            articleService.edit(id, title, mytextarea, file);
        } else {
            String username = request.getRemoteUser();
            articleService.createArticle(title, mytextarea, file, username);

        }
        return "redirect:/articles";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        articleService.deleteById(id);
        return "redirect:/articles";
    }

    @GetMapping("/edit-form/{id}")
    public String editArticlePage(@PathVariable Long id, Model model) {
        if (articleService.findById(id).isPresent()) {
            Article article = articleService.findById(id).get();
            model.addAttribute("article", article);
            model.addAttribute("bodyContent", "add-article");
            return "master-template";
        }
        return "redirect:/articles?error=ProductNotFound";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Object> download(@PathVariable Long id) throws FileNotFoundException {
        Article article = articleService.findById(id).orElseThrow(() -> new ArticleNotFoundException("id"));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + article.getArticleFile().getName() + "\"")
                .contentType(MediaType.valueOf(article.getArticleFile().getContentType()))
                .body(article.getArticleFile().getData());
    }
}
