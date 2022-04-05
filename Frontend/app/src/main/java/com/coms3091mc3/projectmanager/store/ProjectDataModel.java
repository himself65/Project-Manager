package com.coms3091mc3.projectmanager.store;

import androidx.databinding.ObservableField;

import com.coms3091mc3.projectmanager.TasksAdapter;
import com.coms3091mc3.projectmanager.data.Project;

public class ProjectDataModel {
    public ObservableField<Project> project;
    public TasksAdapter tasksAdapter;

    public ProjectDataModel() {
        this.project = new ObservableField<>(new Project(0, "UNKNOWN", ""));
    }
}
