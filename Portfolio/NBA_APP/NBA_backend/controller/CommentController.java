package com.florek.NBA_backend.controller;

import com.florek.NBA_backend.model.entries.Article;
import com.florek.NBA_backend.model.entries.Comment;
import com.florek.NBA_backend.model.people.Person;
import com.florek.NBA_backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("nba/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(path = "{id}")
    public void addComment(@PathVariable ("id") int personID, @Valid @NonNull @RequestBody Comment comment){
        commentService.addComment(personID, comment);
    }

    @DeleteMapping(path = "{authorID}/{commentID}")
    public void deleteComment(@PathVariable ("authorID") int personID, @PathVariable ("commentID") int commentID){
        commentService.deleteComment(personID, commentID);
    }

    @PutMapping(path = "{authorID}/{commentID}")
    public void editComment(@PathVariable ("authorID") int personID, @PathVariable ("commentID") int commentID, @Valid @NonNull @RequestBody Comment newData){
        commentService.editComment(personID, commentID, newData);
    }

    @GetMapping(path = "/article/{articleID}")
    public List<Comment> getCommentsByArticleID(@PathVariable ("articleID") int articleID){
        return commentService.getCommentsByArticleID(articleID);
    }

    @GetMapping(path = "{id}")
    public Optional<Comment> getCommentByID(@PathVariable ("id") int commentID){
        return commentService.getCommentByID(commentID);
    }

    @PutMapping(path = "{id}")
    public void rateComment(@PathVariable ("id") int commentID, @RequestParam (name = "rate") int points){
        commentService.rateComment(commentID, points);
    }
}
