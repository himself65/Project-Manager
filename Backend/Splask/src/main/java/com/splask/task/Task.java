package com.splask.task;

import com.splask.team.Team;
import com.splask.user.User;


import javax.persistence.*;
import com.sun.istack.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Task")
public class Task {

//  Primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	Integer id;
	
	@NotNull
	@Column (name = "task")
	String taskName;
	
	@NotNull
	@Column
	LocalDateTime dateCreated;
	
	@NotNull
	@Column (name = "deadLine")
	String deadLine;
	
	@NotNull
	@Column (name = "dateCompleted")
	String dateCompleted;
	
//	TODO Add Status relationship
//	Task status 
	
//	TODO Add User relationship
//	Task completed by
	@ManyToMany
	List<User> users;

	@ManyToMany
	List<Team> teams;

	Task(){
		dateCreated = LocalDateTime.now();
		users = new ArrayList<>();
		teams = new ArrayList<>();
	}


	public Integer getId() {return id;}

	public void setId(Integer id) {this.id = id;}

	public String getTask() {return taskName;}

	public void setTask(String taskName) {this.taskName = taskName;}

	public String getDateCreated() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return dateCreated.format(format);
	}

	public String getDeadLine() {return deadLine;}

	public void setDeadLine(String deadLine) {this.deadLine = deadLine;}

	public String getDateCompleted() {return dateCompleted;}

	public void setDateCompleted(String dateCompleted) { this.dateCompleted = dateCompleted;}

	public List<User> getUsers()
	{
		return users;
	}














}
