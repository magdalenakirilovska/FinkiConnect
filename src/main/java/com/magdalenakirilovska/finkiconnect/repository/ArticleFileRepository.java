package com.magdalenakirilovska.finkiconnect.repository;

import com.magdalenakirilovska.finkiconnect.model.ArticleFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleFileRepository extends JpaRepository<ArticleFile, Long> {
}
