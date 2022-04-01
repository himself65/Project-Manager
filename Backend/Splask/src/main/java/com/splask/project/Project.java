package com.splask.project;

import com.splask.team.Team;
import com.splask.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Entity
public
class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer projectID;

    @Column
    String projectName;

    @Column
    String deadline;

    @Column
    Boolean status;

    @Column
    String completedBy;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "userID")
//    User creator;
//    
//    @OneToMany
//    List<Team> teams;

    @Column
    String tasks;

    @Column
    LocalDateTime dateCreated;


    Project() {

        dateCreated = LocalDateTime.now();
//        teams = new ArrayList<>();
    }

    public Integer getProjectID() {

        return projectID;
    }


    public String getProjectName()
    {

        return projectName;
    }

    public void setProjectName(String newName)
    {

        projectName = newName;
    }

    public void setDeadline()
    {

    }
    public String getDeadline()
    {
        return deadline;
    }

    public boolean getStatus() {return status;}

    public void setComplete() {
        status = true;
        //setCompletedBy();
    }

    public String getCompletedBy()
    {
        return completedBy;
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


    public String getTasks()
    {
        return tasks;
    }

    public String getDateCreated()
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return dateCreated.format(format);
    }


}
