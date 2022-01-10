package com.florek.NBA_backend.service;

import com.florek.NBA_backend.model.entries.Comment;
import com.florek.NBA_backend.model.people.Person;
import com.florek.NBA_backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleService articleService;
    private final PersonService personService;


    @Autowired
    public CommentService(CommentRepository commentRepository, ArticleService articleService, PersonService personService) {
        this.commentRepository = commentRepository;
        this.articleService = articleService;
        this.personService = personService;
    }

    public void addComment(int personID, Comment comment){
        if(personService.getPersonByID(personID).get().isAbleToComment()){
            commentRepository.save(comment);
        }
    }

    public void deleteComment(int personID, int commentID){
        if(personService.getPersonByID(personID).get().isAdmin() || isAuthor(personService.getPersonByID(personID).get(), commentID)){
            if(commentRepository.findById(commentID).isPresent()){
                commentRepository.deleteById(commentID);
            }
        }
    }

    public void editComment(int personID, int commentID, Comment newComment){
        if(isAuthor(personService.getPersonByID(personID).get(), commentID)){
            commentRepository.findById(commentID).map(comment -> {
                comment.setContent(newComment.getContent());
                return commentRepository.save(comment);
            });
        }
    }

    public List<Comment> getCommentsByArticleID(int articleID){
        if(articleService.getArticleById(articleID).isPresent()){
            List<Comment> list =  commentRepository.findCommentsOfArticle(articleID);
            return list;
        }
        return null;
    }

    public Optional<Comment> getCommentByID(int commentID){return commentRepository.findById(commentID);}

    public void rateComment(int commentID, int points){
        commentRepository.findById(commentID).map(comment -> {
            comment.setPoints(comment.getPoints() + points);
            return commentRepository.save(comment);
        });
    }

    private boolean isAuthor(Person person, int commentID){
        if(getCommentByID(commentID).isPresent()){
            return getCommentByID(commentID).get().getAuthor().getId() == person.getId();
        }
        return false;
    }

}
