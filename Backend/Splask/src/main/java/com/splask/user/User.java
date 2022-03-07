package com.splask.user;

import java.util.Objects;
import javax.persistence.*;
//import javax.validation.constraints.NotBlank;


@Entity
@Table (name = "User")
public class User {
	
/*
 * 	Primary key
 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
//	String id;
	
	@Column 
	String username;
	
	@Column
	String password;
	
	@Column
	String  date;
	
	@Column
	String  time;
	
	@Column
	String author;
	
	@Column
	Boolean loggedIn;
	
//	public user(@NotBlank String userName, @NotBlank String userPassword) {
	public void user(String username, String password) {
		this.username = username;
		this.password = password;
		this.loggedIn = false;
	}

	public Integer getUserId() {return id;}
	
	public void setUserId(int id) {this.id = id;}
	
	public String getUsername() {return username;}
	
	public void setUsername(String username) {this.username = username;}
	
	public String getUserPassword() {return password;}

	public void setUserPassword(String password) {this.password = password;}
	
	public String getDate() {return date;}
	
	public void setDate(String date) {this.date = date;}
	
	public String getTime() {return time;}
	
	public void setTime(String time) {this.time = time;}	
	
	public String getAuthor() {return author;}
	
	public void setAuthor(String author) {this.author = author;}
	
    public boolean isLoggedIn() {return loggedIn;}

    public void setLoggedIn(boolean loggedIn) {this.loggedIn = loggedIn;}
    
    
    /*
     *  to compare an object passed to the program with an object from our database.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    /*
     * This function is used to generate a hash value of our object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, 
                            loggedIn);
    }

    /*
     * Used to return some information about our class object in the form of a String
     */
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", loggedIn=" + loggedIn +
                '}';
    }
	
	
}
