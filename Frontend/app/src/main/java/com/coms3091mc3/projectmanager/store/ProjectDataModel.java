package com.coms3091mc3.projectmanager.store;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.TasksAdapter;
import com.coms3091mc3.projectmanager.data.Project;

/**
 * The type Project data model.
 */
public class ProjectDataModel {
    /**
     * The Project.
     */
    public ObservableField<Project> project;
    /**
     * The Tasks adapter.
     */
    public TasksAdapter tasksAdapter;

    /**
     * Instantiates a new Project data model.
     *
     * @param context the context
     */
    public ProjectDataModel(Context context) {
        this.project = new ObservableField<>(new Project(0, "UNKNOWN", ""));
        this.tasksAdapter = new TasksAdapter(context, R.layout.fragment_task_item);
    }
}
