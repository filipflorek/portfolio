package filip.practice.recipies.controller;

import filip.practice.recipies.model.Recipe;
import filip.practice.recipies.model.RecipeSummary;
import filip.practice.recipies.service.RecipeService;
import filip.practice.recipies.transferObject.JsonErrorResponse;
import filip.practice.recipies.transferObject.JsonRecipeListResponse;
import filip.practice.recipies.transferObject.JsonRecipeResponse;
import filip.practice.recipies.transferObject.JsonResponse;
import filip.practice.recipies.utils.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<JsonResponse> getAllRecipes() throws IOException {
        return new ResponseEntity<>(
                new JsonRecipeListResponse(recipeService.getAllRecipes()),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{recipeName}")
    public ResponseEntity<JsonResponse> getRecipe(@PathVariable String recipeName){
        Optional<RecipeSummary> recipeSummary = recipeService.getRecipe(recipeName);
        return new ResponseEntity<>(
                new JsonRecipeResponse(recipeSummary.orElse(null)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JsonResponse> addRecipe(@Valid @RequestBody Recipe newRecipe){
        Optional<Recipe> addedRecipe = recipeService.addRecipe(newRecipe);
        return addedRecipe.isPresent() ?
                new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(new JsonErrorResponse(ErrorMessage.RECIPE_ALREADY_EXISTS.message), HttpStatus.BAD_REQUEST);
    }

    @PutMapping ResponseEntity<JsonResponse> modifyRecipe(@Valid @RequestBody Recipe updatedRecipe){
        Optional<Recipe> updateRecipe = recipeService.updateRecipe(updatedRecipe);
        return updateRecipe.isPresent() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(new JsonErrorResponse(ErrorMessage.RECIPE_NOT_FOUND.message), HttpStatus.BAD_REQUEST);
    }
}
