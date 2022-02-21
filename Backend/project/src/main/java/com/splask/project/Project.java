package com.splask.project;

import javax.persistence.*;


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
    String projectDeadline;

    @Column
    String dateCompleted;

    @Column
    String creator;

    @Column
    Integer projectProgression;

    @Column
    String tasks;

    public Integer getProjectID() {
        return projectID;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public String getProjectDeadline()
    {
        return projectDeadline;
    }

    public String getDateCompleted()
    {
        return dateCompleted;
    }

    public String getTeam()
    {
        return team;
    }

    public String getCreator()
    {
        return creator;
    }

    public Integer getProjectProgression()
    {
        return projectProgression;
    }

    public String getTasks()
    {
        return tasks;
    }
}
