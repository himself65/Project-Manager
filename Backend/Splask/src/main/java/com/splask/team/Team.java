package com.splask.team;

import com.splask.project.Project;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;


@Entity
public
class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer teamID;

    @Column
    String teamName;

    @Column
    String teamIntro;

    @Column
    String teamUsers;

    @ManyToOne
    @JoinColumn(name = "project")
    @JsonIgnore
    Project teamProjects;


    public Integer getTeamID() {
        return teamID;
    }

    public String getTeamName()
    {
        return teamName;
    }

    public String getTeamIntro;

    public String getTeamUsers() {

        return teamUsers;
    }

    public Project getTeamProjects() {

        return teamProjects;
    }



}
