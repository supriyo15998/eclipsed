package in.example.eclipsed.models;

import com.google.gson.annotations.SerializedName;

public class ErrorsResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("errors")
    private Errors errors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
