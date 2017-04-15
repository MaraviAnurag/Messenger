package org.aakashresearchlabs.messenger;

/**
 * Created by Snehit Sagi on 22-Mar-17.
 */

public class    ChatMessage {
    private long id;
    private boolean isMe;
    private String message;
    private Long userId;
//    private String dateTime;
    private String user;
    private String timestamp;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public boolean getIsme() {
        return isMe;
    }
    public void setMe(boolean isMe) {
        this.isMe = isMe;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
