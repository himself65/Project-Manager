package com.coms3091mc3.projectmanager.store;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.databinding.ObservableField;

import com.coms3091mc3.projectmanager.ProjectAdapter;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.data.Project;

public class ProjectDataModel {
    public ObservableField<Project> project;

    public ProjectDataModel() {
        this.project = new ObservableField<>(new Project(0, "UNKNOWN", ""));
    }
}
