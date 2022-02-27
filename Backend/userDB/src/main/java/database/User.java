package database;

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
	Integer userId;
	
//	@Colum 
	String username;
	
	@Column
	String password;
	
	@Column
	String  dateAndTime;
	
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

	public Integer getUserId() {return userId;}
	
	public void setUserId(int userId) {this.userId = userId;}
	
	public String getUserName() {return username;}
	
	public void setUserName(String userName) {this.username = userName;}
	
	public String getUserPassword() {return password;}

	public void setUserPassword(String userPassword) {this.password = userPassword;}
	
	public String getDateAndTime() {return dateAndTime;}
	
	public void setDateAndTime(String dateAndTime) {this.dateAndTime = dateAndTime;}
	
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
        return Objects.hash(userId, username, password, 
                            loggedIn);
    }

    /*
     * Used to return some information about our class object in the form of a String
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", loggedIn=" + loggedIn +
                '}';
    }
	
	
}
