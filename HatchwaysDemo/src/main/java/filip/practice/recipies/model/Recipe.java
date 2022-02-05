package filip.practice.recipies.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Recipe {

    @NotNull
    @JsonProperty("name")
    private String name;
    @NotNull
    @JsonProperty("ingredients")
    private List<String> ingredients;
    @NotNull
    @JsonProperty("instructions")
    private List<String> instructions;

    public Recipe(String name, List<String> instructions, List<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Recipe() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }
}
