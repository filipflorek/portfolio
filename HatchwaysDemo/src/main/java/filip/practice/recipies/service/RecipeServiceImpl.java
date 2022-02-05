package filip.practice.recipies.service;

import filip.practice.recipies.model.Recipe;
import filip.practice.recipies.model.RecipeSummary;
import filip.practice.recipies.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {


    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<String> getAllRecipes(){
        return recipeRepository.getAllRecipes()
                .stream().map(Recipe::getName).collect(Collectors.toList());
    }

    @Override
    public Optional<RecipeSummary> getRecipe(String recipeName) {

        Optional<RecipeSummary> recipeSummary = Optional.empty();
        Optional<Recipe> maybeRecipe = recipeRepository.getRecipeByName(recipeName);
        if (maybeRecipe.isPresent()) {
            recipeSummary = Optional.of(new RecipeSummary(maybeRecipe.get().getIngredients()));
        }
        return recipeSummary;
    }

    @Override
    public Optional<Recipe> addRecipe(Recipe newRecipe) {
        Optional<Recipe> addedRecipe = Optional.empty();
        Optional<Recipe> duplicatedRecipe = recipeRepository.getRecipeByName(newRecipe.getName());
        if (duplicatedRecipe.isEmpty()) {
            recipeRepository.addRecipe(newRecipe);
            addedRecipe = Optional.of(newRecipe);
        }
        return addedRecipe;
    }

    @Override
    public Optional<Recipe> updateRecipe(Recipe newRecipeData) {
        Optional<Recipe> recipeToUpdate = recipeRepository.getRecipeByName(newRecipeData.getName());
        if(recipeToUpdate.isPresent()){
            recipeRepository.updateRecipe(newRecipeData);
        }
        return recipeToUpdate;
    }
}
