package in.example.eclipsed.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Errors {
    @SerializedName("name")
    private List<String> name;
    @SerializedName("password")
    private List<String> password;
    @SerializedName("username")
    private List<String> username;
    @SerializedName("email")
    private List<String> email;
    @SerializedName("gender")
    private List<String> gender;

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    public List<String> getUsername() {
        return username;
    }

    public void setUsername(List<String> username) {
        this.username = username;
    }
}
