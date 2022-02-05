package filip.practice.recipies.service;

import filip.practice.recipies.model.Recipe;
import filip.practice.recipies.model.RecipeSummary;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface RecipeService {

    List<String> getAllRecipes() throws IOException;

    Optional<RecipeSummary> getRecipe(String recipeName);

    Optional<Recipe> addRecipe(Recipe newRecipe);

    Optional<Recipe> updateRecipe(Recipe updatedRecipe);
}
