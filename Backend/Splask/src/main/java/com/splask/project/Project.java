package com.splask.project;

import com.fasterxml.jackson.annotation.*;
import com.splask.task.Task;
import com.splask.team.Team;
import com.splask.user.User;
import com.sun.istack.NotNull;
import net.minidev.json.JSONObject;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Entity
@Table (name = "Project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer projectID;

    @Column
    String projectName;



    @ManyToMany(mappedBy = "projects")
    @NotNull
    @JsonIgnore
    private List<User> users = new ArrayList<>();


    //One project to Many Teams
    @OneToMany(mappedBy = "teamProject")
    @NotNull
    @JsonIgnore
    private List<Team> teams = new ArrayList<>();

    //One project to Many Tasks
    @OneToMany(mappedBy = "taskProject")
    @NotNull
    @JsonIgnore
    private List<Task> tasks = new ArrayList<>();

    @Column
    String deadline;

    @Column
    Integer status;

    @Column
    String completedBy;

    @Column
    LocalDateTime dateCreated;


    Project() {

        dateCreated = LocalDateTime.now();
        status = 0;
    }

    public Integer getProjectID() {return projectID;}


    public String getProjectName(){return projectName;}
    public void setProjectName(String newName){projectName = newName;}

//  TODO What does this is suppose to be @Chad
    public void setDeadline()
    {

    }
    public String getDeadline(){return deadline;}

    public Integer getStatus() {return status;}

    //TODO
    /*
    public void setComplete() {
        status = true;
        //setCompletedBy();
    }

    //public String getCompletedBy()
    {
        //return completedBy;
    }

    /* public void setCompletedBy() {

        if (status)
        {
            completedBy = t;
        }
    }
*/

    /**
     * formats the date that was made on instantiated
     * @return a formatted date
     */
    public String getDateCreated()
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateCreated.format(format);
    }


    /**
     * Adds new user to Project
     * @param user
     *
     */
    public void enrollUserToProject(User user)
    {

        users.add(user);

    }

    /**
     * Adds new Team to a project while also checking if the team is already in the project
     * @param team
     * @return true if team is already in the project,
     *         false if the team is not in the project, and adds team in the project
     *
     */
    public boolean addTeamToProject(Team team) {

        for (Team i : teams)
        {
            if (i.getTeamName().equals(team.getTeamName()))
            {

                return true;
            }
        }

        teams.add(team);
        return false;
    }

    /**
     * Adds new Task to a project while also checking if the task is already in the prject
     * @param task
     * @return true if task is in project, false if it is not in project as well as adding the task to the project
     */
    public boolean addTaskToProject(Task task) {

        for (Task i : tasks)
        {

            if (i.getTask().equals(task.getTask()))
            {
                return true;

            }
            break;
        }
        tasks.add(task);
        return false;
    }


    /**
     *
     * @return list of users in project
     */
    public List<User> getUsers() {
        return users;}


    public void setUsers(List<User> users) {this.users = users;}

    /**
     *
     * @return list of teams in project
     */
	public List<Team> getTeams() {return teams;}
	public void setTeams(List<Team> teams) {this.teams = teams;}

    /**
     *
     * @return list of tasks in project
     */
	public List<Task> getTasks() {return tasks;}
	public void setTasks(List<Task> tasks) {this.tasks = tasks;}

	
    
    
    
    
    

}
