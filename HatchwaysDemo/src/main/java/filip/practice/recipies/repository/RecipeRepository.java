package filip.practice.recipies.repository;

import filip.practice.recipies.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository {

    List<Recipe> getAllRecipes();

    Optional<Recipe> getRecipeByName(String name);

    void updateRecipe(Recipe recipeToUpdate);

    void addRecipe(Recipe newRecipe);


}
