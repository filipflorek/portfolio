package filip.practice.recipies.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import filip.practice.recipies.model.Recipe;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RecipeListRepository implements RecipeRepository{


    private List<Recipe> recipes = new ArrayList<>();

    @Override
    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    @Override
    public Optional<Recipe> getRecipeByName(String name) {
        return findRecipeByName(name);
    }

    @Override
    public void updateRecipe(Recipe recipeToUpdate) {
        recipes.stream()
                .filter(recipe -> recipe.getName().equals(recipeToUpdate.getName()))
                .forEach(recipe -> {
                    recipe.setIngredients(recipeToUpdate.getIngredients());
                    recipe.setIngredients(recipeToUpdate.getInstructions());
                });
    }

    @Override
    public void addRecipe(Recipe newRecipe) {
        recipes.add(newRecipe);
    }

    private Optional<Recipe> findRecipeByName(String recipeName){
        return recipes.stream()
                .filter(recipe -> recipe.getName().equals(recipeName))
                .findFirst();
    }

    @PostConstruct
    private void readJsonData() throws IOException {
        File resource = new ClassPathResource("data/data.json").getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(resource).get("recipes");
        ObjectReader objectReader = objectMapper.readerFor(new TypeReference<List<Recipe>>(){});
        recipes = objectReader.readValue(node);
    }
}
