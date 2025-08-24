package com.example.eventsservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserEvent extends BaseEvent {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("action")
    private String action="create";

    public UserEvent() {
        super("USER_EVENT");
    }

    public UserEvent(String userId, String username, String email, String action) {
        super("USER_EVENT");
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.action = action;
    }

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}
