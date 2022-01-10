package com.florek.NBA_backend.controller;


import com.florek.NBA_backend.model.entries.Article;
import com.florek.NBA_backend.model.entries.Image;
import com.florek.NBA_backend.model.people.Person;
import com.florek.NBA_backend.service.ArticleService;
import com.florek.NBA_backend.utils.PagedResponse;
import com.florek.NBA_backend.utils.ResponseFile;
import com.florek.NBA_backend.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("nba/articles")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public int addArticle(@Valid @NonNull @RequestBody Article article) throws IOException {
        return articleService.addArticle(article);
    }

    @DeleteMapping(path = "{id}")
    public void deleteArticle(@PathVariable ("id") int articleID){
        articleService.deleteArticle(articleID);
    }

    @PutMapping(path = "{id}")
    public void editArticle(@PathVariable ("id") int articleID, @Valid @NonNull @RequestBody Article newData){
        articleService.editArticle(articleID, newData);
    }

    @GetMapping(path = "{id}")
    public Optional<Article> getArticleByID(@PathVariable ("id") int articleID){
        return articleService.getArticleById(articleID);
    }

    @GetMapping(path = {"/all/{page}"})
    public PagedResponse<Article> getAllArticles(@PathVariable ("page") int pageNumber){
        return articleService.getAllArticles(pageNumber);
    }

    @GetMapping(path = {"/title/{title}/{page}"})
    public List<Article> getArticlesByTitle(@PathVariable ("title") String articleTitle, @PathVariable ("page") int pageNumber){
        return articleService.getArticlesByTitle(articleTitle, pageNumber);
    }

    @GetMapping(path = {"/tags/{tag}/{page}"})
    public PagedResponse<Article> getArticlesByTag(@PathVariable ("tag") String tag, @PathVariable ("page") int pageNumber){
        return articleService.getArticlesByTags(tag, pageNumber);
    }

    @GetMapping(path = {"/category/{category}/{page}"})
    public PagedResponse<Article> getArticlesByCategory(@PathVariable ("category") int categoryID, @PathVariable ("page") int pageNumber){
        return articleService.getArticlesByCategory(categoryID, pageNumber);
    }

    @GetMapping(path = {"/author/{id}"})
    public List<Article> getArticlesByAuthor(@PathVariable("id") int personID){
        return articleService.getArticlesByAuthor(personID);
    }

    @GetMapping(path = {"/popular/{amount}"})
    public List<Article> getMostPopularArticles(@PathVariable ("amount") int amount){
        return articleService.getMostPopularArticles(amount);
    }

    @GetMapping(path = {"/favourite/{id}"})
    public List<Article> getFavouriteArticles(@PathVariable ("id") int userID){
        return articleService.getArticlesByFavourites(userID);
    }

    @PostMapping("/images-upload/{id}")
    public ResponseEntity<ResponseMessage> uploadFile(@Valid @NonNull @RequestBody MultipartFile file, @PathVariable("id") int articleID) {
        String message = "";
        try {
            articleService.addImage(file, articleID);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<List<ResponseFile>> getListFiles(@PathVariable("id") int id) {
        List<ResponseFile> files = articleService.getImagesByArticleID(id).map(image -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/nba/articles/files/")
                    .path(String.valueOf(image.getId()))
                    .toUriString();

            return new ResponseFile(
                    image.getName(),
                    fileDownloadUri,
                    image.getType(),
                    image.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable int id) {
        Image image = articleService.getImage(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                .body(image.getData());
    }
}
