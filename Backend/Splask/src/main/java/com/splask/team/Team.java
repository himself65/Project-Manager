package com.splask.team;

import com.splask.project.Project;
import com.splask.task.Task;
import com.splask.user.User;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

@Entity
@Table (name = "Team")
public
class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    Integer teamID;

    @Column
    String teamName;

    @Column //TODO What is this suppose to be used for? isn't it just the relationship table instead of an object string 
    String teamUsers;

    
    
    
    
    //Many teams to many Projects
	@ManyToOne
	@JsonIgnore
	private List<Project> teamProject = new ArrayList<>();
    
    
    
//	Many Users to many Teams
    @ManyToMany
    @JsonIgnore
    private List<User> ttUsers = new ArrayList<>();

    
    
    
    
    
//  One Team to many Tasks
    @OneToMany(mappedBy = "team")
    @JoinTable(
            name = "Team_Tasks",
            joinColumns = @JoinColumn(name = "team", referencedColumnName = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "task", referencedColumnName = "task_id")
    )
    private List<Task> tasks;
    
    
    
    
    
    

    
    public Integer getTeamID() {return teamID;}
    public void setTeamID(int id) {this.teamID = id;}
    
    public String getTeamName() {return teamName;}
    public void setTeamID(String str) {this.teamName = str;}
    
    public String getTeamUsers() {return teamUsers;}
    public void setTeamUsers(String str) {this.teamUsers = str;}

    
//  Team Controller functions
    public void enrollUser(User user) {ttUsers.add(user);} //adds the user we passed in to the set
    
    
    
//	Relationship tables setters and getters
	public List<Task> getTasks() {return tasks;} //TODO this is a Set, do we want to change it to a List????
	public void setTasks(List<Task> tasks) {this.tasks = tasks;}
	
	
//  Task Controller functions
	public void assignTeamToProject(Project project) {teamProject.add(project);}

	
	

    



}
