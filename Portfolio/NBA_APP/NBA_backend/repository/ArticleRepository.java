package com.florek.NBA_backend.repository;

import com.florek.NBA_backend.model.entries.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query(value = "SELECT COUNT(*) FROM Article WHERE title = ?1", nativeQuery = true)
    int countTitles(String title);

    @Query(value = "SELECT * FROM Article WHERE title LIKE ?1 ORDER BY date_of_publication DESC", nativeQuery = true)
    List<Article> findArticlesByTitle(String title, Pageable pageable);

    @Query(value = "SELECT * FROM Article WHERE id_category = ?1 ORDER BY date_of_publication DESC", nativeQuery = true)
    Page<Article> findArticlesByCategory(int category, Pageable pageable);

    @Query(value = "SELECT * FROM Article WHERE tags ILIKE ?1 ORDER BY date_of_publication DESC", nativeQuery = true)
    Page<Article> findArticlesByTags(String tag, Pageable pageable);

    @Query(value = "SELECT * FROM Article WHERE id_publicist = ?1 ORDER BY date_of_publication DESC", nativeQuery = true)
    List<Article> findArticlesByAuthor(int publicistID);

    @Query(value = "SELECT Article.*  FROM Article JOIN Comment ON article.id = comment.id_article GROUP BY article.id ORDER BY COUNT(comment.id) DESC LIMIT ?1", nativeQuery = true)
    List<Article> getMostPopularArticles(int amount);

    @Query(value = "SELECT * FROM Article ORDER BY date_of_publication DESC", nativeQuery = true)
    Page<Article> findAllByDate(Pageable pageable);
}
