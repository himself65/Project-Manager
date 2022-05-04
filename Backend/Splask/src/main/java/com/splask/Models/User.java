
package com.splask.Models;

//Class imports
import com.fasterxml.jackson.annotation.JsonIgnore;


//Function imports
import com.sun.istack.NotNull;

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
	private Integer userID;

	@NotNull
	@Column (name = "username")
	private String username;

	@NotNull
	@Column (name = "password")
	private String password;

//	@NotNull
	@Column
	LocalDateTime dateCreated;

    @NotNull
	@Column (name = "loggedIn")
	int loggedIn = 0;

	@NotNull
	@Column
	private String fullName;

	@NotNull
	@OneToMany(mappedBy = "completedBy")
	@JsonIgnore
	private List<Task> completedTasks = new ArrayList<>();


	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_project",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "project_id")
	)
	@JsonIgnore
	private List<Project> projects = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_team",
			joinColumns = @JoinColumn(name = "user", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "team", referencedColumnName = "team_id")
	)
	@JsonIgnore
	private List<Team> teams = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_task",
			joinColumns = @JoinColumn(name = "user", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "task", referencedColumnName = "task_id")
	)
	@JsonIgnore
	private List<Task> tasks = new ArrayList<>();

	@Column
	private String imagePath;
	
	



public void user(String username, String password) {

		this.username = username;
		this.password = password;
		this.loggedIn = 0;
		this.imagePath = "/home/splask_files/default.jpg";
	}

	public User(){
		dateCreated = LocalDateTime.now();
//		teams = new ArrayList<>();
	}

	/**
	 * 
	 * @return Integer id of the user
	 */
	public Integer getUserId() {return userID;}
	/**
	 * 
	 * @param id
	 * sets the User id
	 */
	public void setUserId(Integer id) {this.userID = id;}
	
	/**
	 * 
	 * @return String username of the user
	 */
	public String getUsername() {return username;}
	/**
	 * 
	 * @param username
	 * sets the username to the user
	 */
	public void setUsername(String username) {this.username = username;}
	
	/**
	 * 
	 * @return String password of the user
	 */
	public String getUserPassword() {return password;}
	/**
	 * 
	 * @param password
	 * Sets the passoword to the user
	 */
	public void setUserPassword(String password) {this.password = password;}

	/**
	 * 
	 * @return Date that the user was created
	 */
	public String getDateCreated()
	{
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return dateCreated.format(format);
	}
	
	/**
	 * 
	 * @return Integer loggin of the user 
	 */
    public Integer getLoggedIn() {return loggedIn;}
    /**
     * 
     * @param loggedIn
     * Sets the loggin status of the user 
     */
    public void setLoggedIn(int loggedIn) {this.loggedIn = loggedIn;}

    
//	Relationship tables setters and getters
    
    /**
     * 
     * @return user list of Projects
     */
	public List<Project> getProject() {return projects;}
	/**
	 * 
	 * @param projects
	 * new list of Project given to User
	 */
	public void setProject(List<Project> projects) {this.projects = projects;}
	
	/**
	 * 
	 * @return user list of Team
	 */
	public List<Team> getTeam() {return teams;}
	/**
	 * 
	 * @param teams
	 * new list of Team given to User
	 */
	public void setTeams(List<Team> teams) {this.teams = teams;}

	/**
	 * 
	 * @return user list of Task
	 */
	public List<Task> getTasks() {return tasks;}
	/**
	 * 
	 * @param tasks
	 * new list of Task given to User
	 */
	public void setTasks(List<Task> tasks) {this.tasks = tasks;}







	/**
	 * 
	 * @return Full name of the user including fist and last name
	 */
	public String getFullName() {return fullName;}
	/**
	 * 
	 * @param i
	 * Sets the fullname of the user
	 */
	public void setFullName(String i) {fullName = i;}

	
	//Controller Functions
	
	/**
	 * Assigns a new Project to the User
	 * @param project
	 * Project to be assigned
	 */
	public void addProjectToUser(Project project) {projects.add(project);}
	/**
	 * Assigns a new Team to the User
	 * @param team
	 * Team to be assigned
	 */
	public void addTeamToUser(Team team) {teams.add(team);}
	/**
	 * Assigns a new Task to the User
	 * @param task
	 * Task to be assigned
	 */
	public void addTaskToUser(Task task) {tasks.add(task);}


	public String getImagePath()
	{
		return imagePath;
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
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

	// Function that checks if string contains upper case, lower case
	// special character & numeric value
	public static boolean isAllPresent(String str) {

		boolean containsAll = false;
		// ReGex to check if a string contains upper case, lower case
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


	public List<Task> getCompletedTasks() {return completedTasks;}

	public void setCompletedTasks(List<Task> completedTasks) {this.completedTasks = completedTasks;}


}
