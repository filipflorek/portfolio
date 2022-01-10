package com.florek.NBA_backend.model.entries;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.florek.NBA_backend.model.people.Person;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Article extends Entry{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_publicist")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Person publicist;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;
    private String tags;
    private String content;

    @OneToMany(mappedBy="article")

    @JsonManagedReference
    @OrderBy("date_of_publication ASC")
    private Set<Comment> comments;



    public Article(int id, Timestamp date, Person publicist, String title, Category category, String tags, String content) {
        super(id, date);
        this.publicist = publicist;
        this.title = title;
        this.category = category;
        this.tags = tags;
        this.content = content;
        this.dateOfPublication = date;
    }

    public Article(int id, Timestamp dateOfPublication, Person publicist, String title, Category category, String tags, String content, Set<Comment> comments) {
        super(id, dateOfPublication);
        this.publicist = publicist;
        this.title = title;
        this.category = category;
        this.tags = tags;
        this.content = content;
        this.comments = comments;
    }

    public Article(){
        super();
    }

    public Article(int id){
        super(id);
    }


    public Person getPublicist() {
        return publicist;
    }

    public void setPublicist(Person publicist) {
        this.publicist = publicist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

}
