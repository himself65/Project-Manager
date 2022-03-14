package com.splask.project;

import com.splask.team.Team;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "teamID")
    List<Team> teams;

    @Column
    String deadline;

    @Column
    Boolean status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "teamID")
    List<Team> completedBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID")
    String creator;

    @Column
    String tasks;

    @Column
    LocalDateTime dateCreated;


    Project() {

        dateCreated = LocalDateTime.now();
        teams = new ArrayList<>();
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
        setCompletedBy();
    }

    public List<Team> getCompletedBy()
    {
        return completedBy;
    }

    public void setCompletedBy() {
        if (status)
        {
            completedBy = teams;
        }
    }

    public List<Team> getTeam()
    {
        return teams;
    }

    public String getCreator()
    {
        return creator;
    }

    public String getTasks()
    {
        return tasks;
    }

    public LocalDateTime getDateCreated()
    {
        return dateCreated;
    }


}
