package database;

import javax.persistence.*;

@Entity
//@Table
public class User {
	
//	Primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer userId;
	
	@Column
	String userName;
	
	@Column
	String userPassword;
	
	@Column
	String  dateAndTime;
	
	@Column
	String author;

	public Integer getUserId() {return userId;}
	
	public void setUserId(int userId) {this.userId = userId;}
	
	public String getUserName() {return userName;}
	
	public void setUserName(String userName) {this.userName = userName;}
	
	public String getUserPassword() {return userPassword;}

	public void setUserPassword(String userPassword) {this.userPassword = userPassword;}
	
	public String getDateAndTime() {return dateAndTime;}
	
	public void setDateAndTime(String dateAndTime) {this.dateAndTime = dateAndTime;}
	
	public String getAuthor() {return author;}
	
	public void setAuthor(String author) {this.author = author;}

	

}
