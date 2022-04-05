package com.coms3091mc3.projectmanager.data;

public class Task {
    int taskID;
    String taskName;

    public Task(int taskID, String taskName) {
        this.taskID = taskID;
        this.taskName = taskName;
    }

    public int getTaskID() {
        return taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
