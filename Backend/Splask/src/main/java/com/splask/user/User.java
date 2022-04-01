
package com.splask.user;

//Class imports
import com.splask.task.Task;
import com.splask.team.Team;
import com.splask.project.Project;

//Function imports
import com.sun.istack.NotNull;
import net.minidev.json.annotate.JsonIgnore;
//import org.h2.util.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;

@Entity
@Table (name = "User")
public class User {

//  Primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "user_id")
	Integer userID;

	@NotNull
	@Column (name = "username")
	String username;

	@NotNull
	@Column (name = "password")
	String password;

	@NotNull
	@Column
	LocalDateTime dateCreated;

	@NotNull
	@Column (name = "loggedIn")
	Boolean loggedIn = false;

//	@OneToOne
//	@JsonIgnore
//	Project projectsCreated;
//	Set<Project> projectUsers;

	@JsonIgnore
	@ManyToMany(mappedBy = "pUsers")
	@JoinTable(
			name = "users_projects",
			joinColumns = @JoinColumn(name = "user", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "project", referencedColumnName = "project_id")
	)
	private List<Project> projects = new ArrayList<>();

	@JsonIgnore
	@ManyToMany(mappedBy = "ttUsers")
	@JoinTable(
			name = "users_teams",
			joinColumns = @JoinColumn(name = "user", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "team", referencedColumnName = "team_id")
	)
	private List<Team> teams = new ArrayList<>();

	@JsonIgnore
	@ManyToMany(mappedBy = "tUsers")
	@JoinTable(
			name = "users_tasks",
			joinColumns = @JoinColumn(name = "user", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "task", referencedColumnName = "task_id")
	)
	private Set<Task> tasks = new HashSet<>();

//	TODO (DEMO 4) Set relationship with Roles
//	
	
	
	
	
//	public user(@NotBlank String userName, @NotBlank String userPassword) {
//	public void user(String username, String password) { TODO (old) test
public void user(String username, String password, Boolean loggedIn) { //TODO (new)test

	this.username = username;
		this.password = password;
		this.loggedIn = false;
	}

	User(){
		dateCreated = LocalDateTime.now();
//		teams = new ArrayList<>();
	}

	public Integer getUserId() {return userID;}
	public void setUserId(int id) {this.userID = id;}
	
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	
	public String getUserPassword() {return password;}
	public void setUserPassword(String password) {this.password = password;}

	public String getDateCreated()
	{
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return dateCreated.format(format);
	}
	
//	public Project getAuthor() {return projectsCreated;}
//	public void setAuthor(Project author) {this.projectsCreated = author;}
//	
    public boolean isLoggedIn() {return loggedIn;}
    public void setLoggedIn(boolean loggedIn) {this.loggedIn = loggedIn;}
//
////	Class functions
//	public Set<Project> getProjectUsers() {return projectUsers;}
//	public void setProjectUsers(Set<Project> projectUsers) {this.projectUsers = projectUsers;}

	/*
	 TODO
	public Set<Team> getTeam() {return teams;}
	public void setTeams(Set<Team> teams) {this.teams = teams;}

	 */

	public Set<Task> getTasks() {return tasks;}
	public void setTasks(Set<Task> tasks) {this.tasks = tasks;}




//	to compare an object passed to the program with an object from our database.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

//	This function is used to generate a hash value of our object.
    @Override
    public int hashCode() {
        return Objects.hash(userID, username, password,
                            loggedIn);
    }

//  Used to return some information about our class object in the form of a String
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", loggedIn=" + loggedIn +
                '}';
    }

	// Function that checks if string contains uppercase, lowercase
	// special character & numeric value
	public static boolean isAllPresent(String str) {

		boolean containsAll = false;
		// ReGex to check if a string contains uppercase, lowercase
		// special character & numeric value
		String regex = "^(?=.*[a-z])(?=."
				+ "*[A-Z])(?=.*\\d)"
				+ "(?=.*[-+_!@#$%^&*., ?]).+$";

		// Compile the ReGex
		Pattern p = Pattern.compile(regex);

		// If the string is empty show password instructions
		if (str == null) {
			containsAll = false;
	    }

		// Checks length of password. At least 4 characters
		if(str.length() < 4) {
			containsAll = false;
		}

		// Find match between given string & regular expression
		Matcher m = p.matcher(str);
		// Print Yes if string matches ReGex
		if (m.matches()) {
			containsAll = true;
		}else {
			containsAll = false;
		}

		return containsAll;
	}
	
	
}
