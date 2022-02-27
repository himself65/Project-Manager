package com.splask.team;

import javax.persistence.*;


@Entity
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

    @Column
    String teamProjects;


    public Integer getProjectID() {
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

    public String getTeamProjects() {

        return teamProjects;
    }



}
