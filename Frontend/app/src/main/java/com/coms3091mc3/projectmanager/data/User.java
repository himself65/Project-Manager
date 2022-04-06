package com.coms3091mc3.projectmanager.data;

public class User {
    int userID;
    String username;
    String fullname;
    String password;

    public User(int id, String username, String fullname) {
        this.userID = id;
        this.username = username;
        this.fullname = fullname;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {return fullname; }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullname(String fullname) { this.fullname = fullname; }
}
