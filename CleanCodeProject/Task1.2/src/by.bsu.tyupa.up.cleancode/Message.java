package Practice1_2;


public class Message {
    private String id;
    private String author;
    private String message;
    private long timestamp;

    public Message(String id, String author, String message, long timestamp) {
        this.id = id;
        this.author = author;
        this.message = message;
        this.timestamp = timestamp;
    }

    String get_id() {
        return this.id;
    }
    String get_author() {
        return this.author;
    }
    String get_message() {
        return this.message;
    }
    long get_timestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return "id: " + this.id + ", author: " + this.author + ", timestamp: " + this.timestamp + ", message: " + this.message;
    }
}
