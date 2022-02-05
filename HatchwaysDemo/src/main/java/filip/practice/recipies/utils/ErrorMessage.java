package filip.practice.recipies.utils;

public enum ErrorMessage {

    RECIPE_NOT_FOUND("Recipe does not exist"),
    RECIPE_ALREADY_EXISTS("Recipe already exists");

    public final String message;

    private ErrorMessage(String message) {
        this.message = message;
    }
}
