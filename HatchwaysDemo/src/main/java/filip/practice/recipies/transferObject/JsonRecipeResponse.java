package filip.practice.recipies.transferObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import filip.practice.recipies.model.RecipeSummary;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonRecipeResponse extends JsonResponse {

    private RecipeSummary details;

    public JsonRecipeResponse(RecipeSummary details) {
        this.details = details;
    }

    public RecipeSummary getDetails() {
        return details;
    }
}
