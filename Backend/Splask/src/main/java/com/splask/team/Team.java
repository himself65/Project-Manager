package com.splask.team;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.splask.project.Project;
import com.splask.task.Task;
import com.splask.user.User;
import com.sun.istack.NotNull;
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
    
    
//    //Many teams to many Projects
	@ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
	private Project teamProject;
//    
//    
//    
//  Many Users to many Teams
    @ManyToMany(mappedBy = "teams")
    @JsonIgnore
    @NotNull
    private List<User> users = new ArrayList<>();


//  One Team to many Tasks
    @OneToMany(mappedBy = "taskTeam")
    @NotNull
    @JsonIgnore
    private List<Task> tasks;
    





    
    public Integer getTeamID() {return teamID;}
    public void setTeamID(int id) {this.teamID = id;}
    
    public String getTeamName() {return teamName;}

    


    
////  Team Controller functions
    public void enrollUser(User user) {users.add(user);} //adds the user we passed in to the set


//    
//	Relationship tables setters and getters
	public List<Task> getTasks() {return tasks;}
	public void setTasks(List<Task> tasks) {this.tasks = tasks;}
//
    public List<User> getUsers() {return users;}
    public void setTeamUsers(List<User> users) {this.users = users;}
//    
////  Task Controller functions
	public void assignTeamToProject(Project project) {teamProject = project;}


    public Project getTeamProject() {
        return teamProject;
    }

    public void assignTeamToTask(Task task) {
        tasks.add(task);
    }
}
