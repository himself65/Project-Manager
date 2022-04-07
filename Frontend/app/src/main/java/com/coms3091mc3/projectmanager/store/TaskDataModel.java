package com.coms3091mc3.projectmanager.store;

import androidx.databinding.ObservableField;

import com.coms3091mc3.projectmanager.data.Task;

public class TaskDataModel {
    public ObservableField<Task> task;

    public TaskDataModel() {
        this.task = new ObservableField<>(new Task(0, "UNKNOWN"));
    }
}
