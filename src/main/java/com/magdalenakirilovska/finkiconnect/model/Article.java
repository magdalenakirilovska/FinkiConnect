package com.magdalenakirilovska.finkiconnect.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.Date;

@Entity
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotEmpty
    private String content;

    private Instant createdOn;

    private Instant updatedOn;

    @NotBlank
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    private ArticleFile articleFile;

    public Article(@NotBlank String title, @NotEmpty String content, ArticleFile articleFile, @NotBlank String username) {
        this.title = title;
        this.content = content;
        this.createdOn = Instant.now();
        this.updatedOn = Instant.now();
        this.articleFile = articleFile;
        this.username = username;
    }

    public Date getDateCreatedOn() {
        return Date.from(this.createdOn);
    }

    public Date getDateUpdatedOn() {
        return Date.from(this.updatedOn);
    }

    public Article() {
    }
}
