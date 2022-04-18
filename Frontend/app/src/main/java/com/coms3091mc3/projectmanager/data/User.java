package com.coms3091mc3.projectmanager.data;

/**
 * The type User.
 */
public class User {
    /**
     * The User id.
     */
    int userID;
    /**
     * The Username.
     */
    String username;
    /**
     * The Fullname.
     */
    String fullname;
    /**
     * The Password.
     */
    String password;

    /**
     * Instantiates a new User.
     *
     * @param id       the id
     * @param username the username
     * @param fullname the fullname
     */
    public User(int id, String username, String fullname) {
        this.userID = id;
        this.username = username;
        this.fullname = fullname;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets fullname.
     *
     * @return the fullname
     */
    public String getFullname() {return fullname; }

    /**
     * Sets user id.
     *
     * @param userID the user id
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets fullname.
     *
     * @param fullname the fullname
     */
    public void setFullname(String fullname) { this.fullname = fullname; }
}
