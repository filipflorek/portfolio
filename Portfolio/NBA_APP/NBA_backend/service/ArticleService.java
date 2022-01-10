package com.florek.NBA_backend.service;

import com.florek.NBA_backend.model.basketball.Player;
import com.florek.NBA_backend.model.basketball.Team;
import com.florek.NBA_backend.model.entries.Article;
import com.florek.NBA_backend.model.entries.Category;
import com.florek.NBA_backend.model.entries.Image;
import com.florek.NBA_backend.model.people.Person;
import com.florek.NBA_backend.repository.ArticleRepository;
import com.florek.NBA_backend.repository.ImageRepository;
import com.florek.NBA_backend.utils.PagedResponse;
import com.florek.NBA_backend.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ImageRepository imageRepository;
    private final CategoryService categoryService;
    private final PersonService personService;
    private final int articlesOnSinglePage = 5;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ImageRepository imageRepository, CategoryService categoryService, PersonService personService){
        this.articleRepository = articleRepository;
        this.imageRepository = imageRepository;
        this.categoryService = categoryService;
        this.personService = personService;
    }

    public int addArticle(Article article) throws IOException {
        if(articleRepository.findById(article.getId()).isEmpty() && isTitleUnique(article.getTitle())){
            articleRepository.save(article);
        }
        return article.getId();
    }

    public void deleteArticle(int articleID){
        if(articleRepository.findById(articleID).isPresent()){
            articleRepository.deleteById(articleID);
        }
    }

    public void editArticle(int articleID, Article newArticle){
        articleRepository.findById(articleID)
                .map(article -> {
                    article.setTitle(newArticle.getTitle());
                    article.setCategory(newArticle.getCategory());
                    article.setContent(newArticle.getContent());
                    article.setTags(newArticle.getTags());
                    return articleRepository.save(article);
                });
    }

    public Optional<Article> getArticleById(int articleID){ return articleRepository.findById(articleID);}

    public List<Article> getArticlesByTitle(String title, int page){
        return articleRepository.findArticlesByTitle(Utilities.prepareSearchString(title),PageRequest.of(page,articlesOnSinglePage));}

    public PagedResponse<Article> getAllArticles(int pageNumber){
        Page<Article> page = articleRepository.findAllByDate(PageRequest.of(pageNumber,articlesOnSinglePage));
        return new PagedResponse<Article>(page.getContent(), pageNumber, page.getTotalPages(), (int)page.getTotalElements(), page.isLast());
    }

    public PagedResponse<Article> getArticlesByCategory(int categoryID, int pageNumber){
        if (categoryService.getCategoryById(categoryID).isPresent()){
            Page<Article> page = articleRepository.findArticlesByCategory(categoryID, PageRequest.of(pageNumber,articlesOnSinglePage));
            return new PagedResponse<Article>(page.getContent(), pageNumber, page.getTotalPages(), (int)page.getTotalElements(), page.isLast());
        }
        return null;
    }

    public PagedResponse<Article> getArticlesByTags(String tag, int pageNumber){
        Page<Article> page = articleRepository.findArticlesByTags(Utilities.prepareSearchString(tag), PageRequest.of(pageNumber,articlesOnSinglePage));
        return new PagedResponse<Article>(page.getContent(), pageNumber, page.getTotalPages(), (int)page.getTotalElements(), page.isLast());}

    public List<Article> getArticlesByAuthor(int personID){
        if(personService.getPersonByID(personID).isPresent()){
            if(personService.getPersonByID(personID).get().isPublicist()) {
                List<Article> articles =  articleRepository.findArticlesByAuthor(personID);
                articles.forEach(article -> article.setComments(null));
                return articles;
            }
        }
        return null;
    }

    public List<Article> getMostPopularArticles(int amount){
        return articleRepository.getMostPopularArticles(amount);
    }

    public List<Article> getArticlesByFavourites(int userID){
        List<Player> players = personService.getFavouritePlayers(userID);
        List<Team> teams = personService.getFavouriteTeams(userID);
        List<String> favList = prepareFavouritesList(players, teams);
        return articleRepository.findAll().stream().filter(article -> favList.stream().anyMatch(article.getTags()::contains)).collect(Collectors.toList());
    }

    public Image addImage(MultipartFile file, int articleID) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Image image = new Image(fileName, file.getContentType(), file.getBytes(), new Article(articleID));

        return imageRepository.save(image);
    }

    public Image getImage(int id) {
        return imageRepository.findById(id).get();
    }


    public Stream<Image> getImagesByArticleID(int articleID) {
        return imageRepository.findAll().stream().filter(image -> image.getArticle().getId() == articleID);
    }

    private boolean isTitleUnique(String title){
        return articleRepository.countTitles(title) == 0;
    }

    private List<String> prepareFavouritesList(List<Player> players, List<Team> teams){
        ArrayList<String> favList = new ArrayList<>();
        for (Player p : players) {
            favList.add(p.getLastName());
        }
        for(Team t : teams){
            favList.add(t.getName());
        }
        return favList;
    }
}
