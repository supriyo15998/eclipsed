package in.example.eclipsed.models;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private String gender;
    private String username;
    private String password;
    private String password_confirmation;
    private int messages_count;

    public int getMessages_count() {
        return messages_count;
    }

    public void setMessages_count(int messages_count) {
        this.messages_count = messages_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(String name, String username, String email, String gender, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
