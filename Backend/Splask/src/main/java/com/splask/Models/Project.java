package com.splask.Models;

import com.fasterxml.jackson.annotation.*;
import com.sun.istack.NotNull;
import net.minidev.json.JSONArray;
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
    private String projectName;

    @OneToMany(mappedBy = "project")
    @NotNull
    @JsonIgnore
    private List<Announcements> adminMessages = new ArrayList<>();


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
    Integer adminId;

    @Column
    Integer status;

    @Column
    LocalDateTime dateCreated;


    public Project() {
        dateCreated = LocalDateTime.now();
        status = 0;
    }

    public Integer getProjectID() {return projectID;}


    public String getProjectName(){return projectName;}
    public void setProjectName(String newName){projectName = newName;}

    public Integer getAdmin(){return adminId;}
    public void setAdmin(Integer id){this.adminId = id;}

    public Integer getStatus() {return status;}

    public void completeProject()
    {
        status = 1;
    }

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
     * @param user user to be added to project
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
     * Adds new Task to a project while also checking if the task is already in the project
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


    public List<Announcements> getAdminMessages() {
        return adminMessages;
    }

    public void addAdminMessages(Announcements newMessage)
    {

        adminMessages.add(newMessage);
    }


}
