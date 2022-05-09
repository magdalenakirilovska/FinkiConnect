/*
package com.magdalenakirilovska.finkiconnect.controller;

import com.magdalenakirilovska.finkiconnect.service.ArticlePerUserService;
import com.magdalenakirilovska.finkiconnect.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts-per-user")
public class PostsPerUserController {

    private final ArticlePerUserService articlePerUserService;
    private final UserService userService;

    public PostsPerUserController(ArticlePerUserService articlePerUserService, UserService userService) {
        this.articlePerUserService = articlePerUserService;
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public String getPostsPerUserPage(@PathVariable String username, Model model) {
        model.addAttribute("posts", articlePerUserService.listAllArticles(username));
        model.addAttribute("bodyContent", "posts-per-user");
        return "master-template";
    }
}
*/
