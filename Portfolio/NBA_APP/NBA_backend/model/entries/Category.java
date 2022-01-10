package com.florek.NBA_backend.model.entries;


import javax.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String category;

    public Category(int id, String category) {
        this.id = id;
        this.category = category;
    }
    public Category(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
