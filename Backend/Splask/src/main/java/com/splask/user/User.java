
package com.splask.user;

//Class imports
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class User{

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

//	@NotNull
	@Column
	LocalDateTime dateCreated;

    @NotNull
	@Column (name = "loggedIn")
	Integer loggedIn = 0;

	@NotNull
	@Column(name = "full_name")
	private String fullName;


	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_project",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "project_id")
	)
	@JsonIgnore
	private List<Project> projects = new ArrayList<>();
////
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_team",
			joinColumns = @JoinColumn(name = "user", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "team", referencedColumnName = "team_id")
	)
	@JsonIgnore
	private List<Team> teams = new ArrayList<>();
//
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_task",
			joinColumns = @JoinColumn(name = "user", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "task", referencedColumnName = "task_id")
	)
	@JsonIgnore
	private List<Task> tasks = new ArrayList<>();

//	TODO (DEMO 4) Set relationship with Roles
//	
	
	
	
	
//	public user(@NotBlank String userName, @NotBlank String userPassword) {
//	public void user(String username, String password) { TODO (old) test
public void user(String username, String password, Boolean loggedIn) { //TODO (new)test

		this.username = username;
		this.password = password;
		this.loggedIn = 0;
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
	

    public Integer isLoggedIn() {return loggedIn;}
    public void setLoggedIn(Integer loggedIn) {this.loggedIn = loggedIn;}

    
////	Relationship tables setters and getters
//	public List<Project> getProject() {return projects;}
//	public void setProject(List<Project> projects) {this.projects = projects;}


	public List<Team> getTeam() {return teams;}
	public void setTeams(List<Team> teams) {this.teams = teams;}


	public List<Task> getTasks() {return tasks;}
	public void setTasks(List<Task> tasks) {this.tasks = tasks;}


	public String getFullName() {return fullName;}
	public void setFullName(String i) {fullName = i;}

	//Controller Functions
	public void addProjectToUser(Project project)
	{
		System.out.println(Arrays.toString(projects.toArray()));
		projects.add(project);
		System.out.println(Arrays.toString(projects.toArray()));
	}




	
	
	

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
