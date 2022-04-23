package com.coms3091mc3.projectmanager.data;

/**
 * The type Team.
 */
public class Team {
    /**
     * The Team id.
     */
    int teamID;
    /**
     * The Team name.
     */
    String teamName;

    /**
     * Instantiates a new Team.
     *
     * @param id   the id
     * @param name the name
     */
    public Team(int id, String name) {
        this.teamID = id;
        this.teamName = name;
    }

    /**
     * Gets team id.
     *
     * @return the team id
     */
    public int getTeamID() {
        return teamID;
    }

    /**
     * Gets team name.
     *
     * @return the team name
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Sets team id.
     *
     * @param teamID the team id
     */
    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    /**
     * Sets team name.
     *
     * @param teamName the team name
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
