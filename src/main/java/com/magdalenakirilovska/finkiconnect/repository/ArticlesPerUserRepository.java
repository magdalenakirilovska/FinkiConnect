package com.magdalenakirilovska.finkiconnect.repository;

import com.magdalenakirilovska.finkiconnect.model.ArticlesPerUser;
import com.magdalenakirilovska.finkiconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ArticlesPerUserRepository extends JpaRepository<ArticlesPerUser, Long> {
    Optional<ArticlesPerUser> findByUser(User user);
    Optional<ArticlesPerUser> findByUserUsername(String username);
}
