package com.coms3091mc3.projectmanager.data;

/**
 * The type Task.
 */
public class Task {
    /**
     * The Task id.
     */
    int taskID;
    /**
     * The Task name.
     */
    String taskName;
    /**
     * The Status.
     */
    int status;
    /**
     * The Team name.
     */
    String teamName;

    /**
     * Instantiates a new Task.
     *
     * @param taskID   the task id
     * @param taskName the task name
     */
    public Task(int taskID, String taskName) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.status = 0;
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
     * Sets team name.
     *
     * @param teamName the team name
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets task id.
     *
     * @return the task id
     */
    public int getTaskID() {
        return taskID;
    }

    /**
     * Gets task name.
     *
     * @return the task name
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Sets task id.
     *
     * @param taskID the task id
     */
    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    /**
     * Sets task name.
     *
     * @param taskName the task name
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
