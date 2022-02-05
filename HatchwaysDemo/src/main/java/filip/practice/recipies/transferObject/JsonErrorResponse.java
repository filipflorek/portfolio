package filip.practice.recipies.transferObject;

public class JsonErrorResponse extends JsonResponse{

    private String error;

    public JsonErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
