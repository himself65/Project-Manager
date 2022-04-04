package com.splask.project;

import com.splask.task.Task;
import com.splask.team.Team;
import com.splask.user.User;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table (name = "Project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer projectID;

    @Column
    String projectName;

    
//    //Many Projects to Many Users
//    @ManyToMany
//    @JsonIgnore
//    private List<User> pUsers = new ArrayList<>();
//
//
//    //One project to Many Teams
//    @OneToMany(mappedBy = "teamProject")
//    @JoinTable(
//            name = "teams_in_project",
//            joinColumns = @JoinColumn(name = "project", referencedColumnName = "project_id"),
//            inverseJoinColumns =  @JoinColumn(name = "team", referencedColumnName = "team_id")
//    )
//    private List<Team> teams = new ArrayList<>();

//
//    //One project to Many Tasks
//    @OneToMany(mappedBy = "project")
//    @JsonIgnore
//    @JoinTable(
//            name = "project_tasks",
//            joinColumns = @JoinColumn(name = "project", referencedColumnName = "project_id"),
//            inverseJoinColumns = @JoinColumn(name = "task", referencedColumnName = "task_id")
//    )
//    private List<Task> tasks = new ArrayList<>();





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
//    public List<Team> getTeam()
//    {
//        return teams;
//    }


    /*public String getTasks()
    {
        return tasks;
    }

     */

    public String getDateCreated()
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateCreated.format(format);
    }

////  Controller function
//	public void enrollUserToProject(User user) {pUsers.add(user);}
//
//    //Controller function
//    public void addTeamToProject(Team team) {teams.add(team);}
//
//    //Controller function
//    public void addTaskToProject(Task task) {tasks.add(task);}
//    
//
////	Relationship tables setters and getters
//    public List<User> getUsers() {return pUsers;}
//    public void setUsers(List<User> users) {this.pUsers = users;}
//
//	public List<Team> getTeams() {return teams;}
//	public void setTeams(List<Team> teams) {this.teams = teams;}
//
//	public List<Task> getTasks() {return tasks;} 
//	public void setTasks(List<Task> tasks) {this.tasks = tasks;}
//    
	
    
    
    
    
    

}
