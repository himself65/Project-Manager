package com.splask.team;

import com.splask.project.Project;
import com.splask.task.Task;
import com.splask.user.User;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.JoinColumn;

import org.springframework.stereotype.Repository;

@Entity
@Table (name = "Team")
public
class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    Integer teamID;

    @Column
    String teamName;

    @Column
    String teamUsers;

    //Many teams to many Users
    @ManyToOne
    @JsonIgnore
    Project teamProject;

//	Many Users to many Teams
    @ManyToMany
    @JsonIgnore
    private List<User> ttUsers = new ArrayList<>();

//  One Team to many Tasks
    @OneToMany(mappedBy = "team")
    @JsonIgnore
    @JoinTable(
            name = "Team_Tasks",
            joinColumns = @JoinColumn(name = "team", referencedColumnName = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "task", referencedColumnName = "task_id")
    )
    private Set<Task> tasks;

    public Integer getTeamID() {
        return teamID;
    }

    public String getTeamName()
    {
        return teamName;
    }

    public String getTeamIntro;

//    Class functions
    public String getTeamUsers() {return teamUsers;}

//    public Project getTeamProjects() {return teamProjects;}

    /*
    TODO
    public void enrollUser(User user) {teamUser.add(user);} //adds the user we passed in to the set

     */


}
