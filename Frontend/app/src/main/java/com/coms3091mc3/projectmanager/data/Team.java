package com.coms3091mc3.projectmanager.data;

public class Team {
    int teamID;
    String teamName;

    public Team(int id, String name) {
        this.teamID = id;
        this.teamName = name;
    }

    public int getTeamID() {
        return teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
