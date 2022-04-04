package com.splask.task;

import com.splask.project.Project;
import com.splask.team.Team;
import com.splask.user.User;


import javax.persistence.*;
import com.sun.istack.NotNull;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table (name = "Task")
public class Task {

//  Primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "task_id")
	Integer taskID;
	
	@NotNull
	@Column (name = "task")
	String taskName;

	@NotNull
	@Column (name = "status")
	Boolean status;
	
	@NotNull
	@Column
	LocalDateTime dateCreated;
	
	@NotNull
	@Column (name = "deadLine")
	String deadLine;
	
	@NotNull
	@Column (name = "dateCompleted")
	String dateCompleted;

	
////	Many task to many users
//	@ManyToMany
//	@JsonIgnore
//	private List<User> tUsers = new ArrayList<>();
//
////	Many tasks to one Team
//	@ManyToOne
//	@JsonIgnore
//	private List<Team> taskTeam = new ArrayList<>();
//
////  Many teams to many Projects
//	@ManyToOne
//	@JsonIgnore
//	private List<Project> taskProject = new ArrayList<>();

	
	
	
	
	public Task(){
		dateCreated = LocalDateTime.now();

	}


	public Integer getId() {return taskID;}

	public void setId(Integer id) {this.taskID = id;}

	public String getTask() {return taskName;}

	public void setTask(String taskName) {this.taskName = taskName;}

	public Boolean getStatus() {return status;}

	public void setStatus(boolean status){ this.status = status;}

	public String getDateCreated() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return dateCreated.format(format);
	}

	public String getDeadLine() {return deadLine;}

	public void setDeadLine(String deadLine) {this.deadLine = deadLine;}

	public String getDateCompleted() {return dateCompleted;}

	public void setDateCompleted(String dateCompleted) { this.dateCompleted = dateCompleted;}
/*
TODO

	public void setUserTasks(Set<User> userTasks) {this.userTasks = userTasks;}
*/

	
////  Task Controller functions
//	public void assignUser(User user) {
//		tUsers.add(user); //adds the user we passed in to the set
//		
//	}
//	
//	public void assignTaskToTeam(Team team) {taskTeam.add(team);}
//
//	public void assignTaskToProject(Project project) {taskProject.add(project);}





}
