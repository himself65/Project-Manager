package com.coms3091mc3.projectmanager.store;

import androidx.databinding.ObservableField;

import com.coms3091mc3.projectmanager.data.Task;

/**
 * The type Task data model.
 */
public class TaskDataModel {
    /**
     * The Task.
     */
    public ObservableField<Task> task;

    /**
     * Instantiates a new Task data model.
     */
    public TaskDataModel() {
        this.task = new ObservableField<>(new Task(0, "UNKNOWN"));
    }
}
