package filip.practice.recipies.model;

import java.util.List;

public class RecipeSummary {

    private List<String> ingredients;
    private int numSteps;

    public RecipeSummary(List<String> ingredients) {
        this.ingredients = ingredients;
        numSteps = ingredients.size();
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public int getNumSteps() {
        return numSteps;
    }
}
