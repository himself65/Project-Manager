package UserDB;

import javax.persistence.*;

public class userDB {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer userId;
	
	@Column
	String userName;
	
	@Column
	String password;
	
	@Column
	String dateAndTime;
	
	@Column
	String author;

	public Integer getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}

	
	public String getDateAndTime() {
		return dateAndTime;
	}
	
	public String getAuthor() {
		return author;
	}
	

}
