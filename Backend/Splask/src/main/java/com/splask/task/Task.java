package com.splask.task;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splask.project.Project;
import com.splask.team.Team;
import com.splask.user.User;


import javax.persistence.*;
import com.sun.istack.NotNull;


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
	Integer status;
	
	@NotNull
	@Column
	LocalDateTime dateCreated;
	
	@NotNull
	@Column (name = "deadLine")
	String deadLine;
	
	@NotNull
	@Column (name = "dateCompleted")
	String dateCompleted;

	@NotNull
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private User completedBy;

	
//	Many task to many users
	@ManyToMany(mappedBy = "tasks")
	@JsonIgnore
	private List<User> users = new ArrayList<>();
//
//	Many tasks to one Team
	@ManyToOne
	@JoinColumn(name = "team_id")
	@JsonIgnore
	private Team taskTeam;
//
//  Many tasks to one Project
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	@JsonIgnore
	private Project taskProject;

	
	
	
	
	public Task(){
		dateCreated = LocalDateTime.now();
		status = 0;

	}


	public Integer getId() {return taskID;}

	public void setId(Integer id) {this.taskID = id;}

	public String getTask() {return taskName;}

	public void setTask(String taskName) {this.taskName = taskName;}

	public Integer getComplete() {return status;}

	public void setComplete(User user) {


		this.status = 1;
		completedBy = user;
	}

	public String getDateCreated() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return dateCreated.format(format);
	}



	public String getDeadLine() {return deadLine;}

	public void setDeadLine(String deadLine) {this.deadLine = deadLine;}

	public String getDateCompleted() {return dateCompleted;}

	public void setDateCompleted(String dateCompleted) { this.dateCompleted = dateCompleted;}

	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users)
	{
		this.users = users;
	}

	public Project getTaskProject(){return taskProject;}
	public void setTaskProject(Project project) {taskProject = project;}

	public Team getTaskTeam(){return taskTeam;}
	public void setTaskTeam(Team team) {taskTeam = team;}

	//  Task Controller functions
	public void assignUser(User user) {
		users.add(user); //adds the user we passed in to the set

	}

	public void assignTaskToTeam(Team team) {taskTeam = team;}

	public void assignTaskToProject(Project project) {taskProject = project;}





}
