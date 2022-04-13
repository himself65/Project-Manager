package com.splask.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
	@Column (name = "task_id")
	Integer taskID;
	
	@NotNull
	@Column (name = "task")
	private String taskName;

	@NotNull
	@Column (name = "status")
	private Integer status;
	
	@NotNull
	@Column
	private final LocalDateTime dateCreated;
	
	@NotNull
	@Column (name = "deadLine")
	private String deadLine;
	
	@NotNull
	@Column (name = "dateCompleted")
	private String dateCompleted;

	@NotNull
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private User completedBy;

	
//	Many task to many users
	@ManyToMany(mappedBy = "tasks")
	@JsonIgnore
	private List<User> users = new ArrayList<>();

//	Many tasks to one Team
	@ManyToOne
	@JoinColumn(name = "team_id")
	@JsonIgnore
	private Team taskTeam;

//  Many tasks to one Project
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	@JsonIgnore
	private Project taskProject;

	
	public Task(){
		dateCreated = LocalDateTime.now();
		status = 0;

	}

	/**
	 * 
	 * @return Integer id of the task
	 */
	public Integer getId() {return taskID;}
	/**
	 * 
	 * @param id
	 * new id for the task to be set 
	 */
	public void setId(Integer id) {this.taskID = id;}

	/**
	 * 
	 * @return String of the task
	 */
	public String getTask() {return taskName;}
	/**
	 * 
	 * @param taskName
	 * Set new Task
	 */
	public void setTask(String taskName) {this.taskName = taskName;}

	/**
	 * 
	 * @return Integer status of the task, 1 is completed / 0 is incomplete
	 */
	public Integer getComplete() {return status;}
	/**
	 * 
	 * @param user
	 * Sets the task to complete
	 */
	public void setComplete(User user) {
		this.status = 1;
		completedBy = user;
	}

	/**
	 * 
	 * @return Date that the task was created
	 */
	public String getDateCreated() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return dateCreated.format(format);
	}

	/**
	 * 
	 * @return Deadline of the task
	 */
	public String getDeadLine() {return deadLine;}
	/**
	 * 
	 * @param deadLine
	 * Sets the deadline of the task
	 */
	public void setDeadLine(String deadLine) {this.deadLine = deadLine;}

	/**
	 * 
	 * @return Date that the task was completed 
	 */
	public String getDateCompleted() {return dateCompleted;}
	/**
	 * 
	 * @param dateCompleted
	 * sets the date that the task was completed 
	 */
	public void setDateCompleted(String dateCompleted) { this.dateCompleted = dateCompleted;}

	/**
	 * 
	 * @return list of Users in the tasks
	 */
	public List<User> getUsers() {return users;}
	/**
	 * 
	 * @param users
	 * new User list
	 * new list of Users given to Task
	 */
	public void setUsers(List<User> users){this.users = users;}

	/**
	 * 
	 * @return Task in the project
	 */
	public Project getTaskProject(){return taskProject;}
	/**
	 * 
	 * @param project
	 * Sets the task to the project
	 */
	public void setTaskProject(Project project) {taskProject = project;}

	/**
	 * 
	 * @return Task of the Team
	 */
	public Team getTaskTeam(){return taskTeam;}
	/**
	 * 
	 * @param team sets the Task to the Team
	 */
	public void setTaskTeam(Team team) {taskTeam = team;}

	
	
	//  Task Controller functions
	/**
	 * 
	 * @param user
	 * user to be assigned
	 */
	public void assignUser(User user) {users.add(user);}

	public void assignTaskToTeam(Team team) {taskTeam = team;}

	public void assignTaskToProject(Project project) {taskProject = project;}





}
