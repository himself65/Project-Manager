package com.coms3091mc3.projectmanager.store;

import androidx.databinding.ObservableArrayList;

import com.coms3091mc3.projectmanager.data.Project;

public class ProjectStore {
    public ObservableArrayList<Project> projects;

    public ProjectStore() {
        this.projects = new ObservableArrayList<>();
    }
}
