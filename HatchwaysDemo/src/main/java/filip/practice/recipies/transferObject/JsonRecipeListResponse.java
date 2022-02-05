package filip.practice.recipies.transferObject;

import java.util.List;

public class JsonRecipeListResponse extends JsonResponse{

    private List<String> recipeNames;

    public JsonRecipeListResponse(List<String> recipeNames) {
        this.recipeNames = recipeNames;
    }

    public List<String> getRecipeNames() {
        return recipeNames;
    }
}
