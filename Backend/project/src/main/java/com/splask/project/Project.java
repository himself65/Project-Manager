package com.splask.project;

import javax.persistence.*;
import java.time.LocalDateTime;



@Entity
class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer projectID;

    @Column
    String projectName;

    @Column
    String team;

    @Column
    String deadline;

    @Column
    String completedBy;

    @Column
    String creator;

    @Column
    String tasks;

    @Column
    LocalDateTime dateCreated;


    Project() {
        dateCreated = LocalDateTime.now();
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

    public String getCompletedBy()
    {
        return completedBy;
    }

    public String getTeam()
    {
        return team;
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
