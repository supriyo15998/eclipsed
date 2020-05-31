package in.example.eclipsed.models;

public class Message {
    private int id;
    private int user_id;
    private String message;
    private int isRead;

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return isRead == 1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRead(int read) {
        isRead = read;
    }
}
