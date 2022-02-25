package com.coms3091mc3.projectmanager.data;

import java.io.Serializable;

public class Project implements Serializable {
    private int id;
    private String name;
    private String createdDate;

    public Project(int id, String name, String createdDate) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getName() {
        return name;
    }
}
