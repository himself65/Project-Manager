package com.coms3091mc3.projectmanager.store;

import androidx.databinding.ObservableField;

import com.coms3091mc3.projectmanager.data.Team;

/**
 * The type Team data model.
 */
public class TeamDataModel {
    /**
     * The Team.
     */
    public ObservableField<Team> team;

    /**
     * Instantiates a new Team data model.
     */
    public TeamDataModel() {
        this.team = new ObservableField<>(new Team(0, "UNKNOWN"));
    }
}
