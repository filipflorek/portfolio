package com.florek.NBA_backend.repository;

import com.florek.NBA_backend.model.entries.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "SELECT * FROM Comment WHERE id_article = ?1", nativeQuery = true)
    List<Comment> findCommentsOfArticle(int articleID);
}
