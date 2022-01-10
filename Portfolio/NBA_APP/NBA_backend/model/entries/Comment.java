package com.florek.NBA_backend.model.entries;


import com.fasterxml.jackson.annotation.*;
import com.florek.NBA_backend.model.people.Person;
import org.springframework.web.servlet.View;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Comment extends Entry{

    private String content;
    private int points;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_article")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonBackReference
    private Article article;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Person author;

    public Comment(int id, Timestamp dateOfPublication, String content, int points, Article article, Person author) {
        super(id, dateOfPublication);
        this.content = content;
        this.points = points;
        this.article = article;
        this.author = author;
    }

    public Comment(){}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }
}
