package com.splask.team;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splask.project.Project;
import com.splask.task.Task;
import com.splask.user.User;
import com.sun.istack.NotNull;


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
    @JsonIgnore
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


    /**
     *
     * @return the Integer id of the team
     */
    public Integer getTeamID() {return teamID;}

    /**
     *
     * @param id
     * new id for the user to be set as
     */
    public void setTeamID(int id) {this.teamID = id;}

    /**
     *
     * @return the teamName of the project
     */
    public String getTeamName() {return teamName;}

    /**
     * sets a new name for the team
     * @param name
     * new name of the team
     */
    public void setTeamName(String name) {teamName = name;}


    /**
     * Adds a new user to a team
     * @param user
     * user to be added
     */
    public void enrollUser(User user) {users.add(user);} //adds the user we passed in to the set


    /**
     *
     * @return the teams list of Tasks
     */
	public List<Task> getTasks() {return tasks;}

    /**
     * updates whole task list
     * @param tasks
     * new list of Tasks given to team
     */
	public void setTasks(List<Task> tasks) {this.tasks = tasks;}

    /**
     *
     * @return the Teams list of Users in the team
     */
    public List<User> getUsers() {return users;}

    /**
     * changes the list of users in the team
     * @param users
     * new user list
     */
    public void setTeamUsers(List<User> users) {this.users = users;}
//    

    /**
     * Assigns the team to the given project
     * @param project
     * project that the team is being assigned to
     */
	public void assignTeamToProject(Project project) {teamProject = project;}

    /**
     *
     * @return the project that the Team is in
     */
    public Project getTeamProject() {
        return teamProject;
    }

    /**
     * assigns a new task to the team
     * @param task
     * task to be assigned
     */
    public void assignTeamToTask(Task task) {
        tasks.add(task);
    }
}
