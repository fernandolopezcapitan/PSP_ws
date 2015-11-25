package com.salesianostriana.psp.parsedotcomlogin;

/**
 * Created by Luismi on 21/11/2015.
 */
public class User {

    private String objectId;
    private String username;
    private String name;
    private String sessionToken;

    public User() {
    }

    public User(String name, String objectId, String sessionToken, String username) {
        this.name = name;
        this.objectId = objectId;
        this.sessionToken = sessionToken;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", objectId='" + objectId + '\'' +
                ", username='" + username + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                '}';
    }
}
