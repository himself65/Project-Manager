package com.coms3091mc3.projectmanager.store;

import androidx.databinding.ObservableField;

import com.coms3091mc3.projectmanager.data.Team;

public class TeamDataModel {
    public ObservableField<Team> team;

    public TeamDataModel() {
        this.team = new ObservableField<>(new Team(0, "UNKNOWN"));
    }
}
