package com.splask.team;

import com.splask.project.Project;
import com.splask.task.Task;
import com.splask.user.User;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
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

    @ManyToOne
    @JoinColumn(name = "project")
    @JsonIgnore
    Project teamProjects;

//	Many Users to many Teams
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "usersInTeam",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> teamUser = new HashSet<>();

//  One Team to many Tasks
    @OneToMany(mappedBy = "team")
    @JsonIgnore
    @JoinTable(
            name = "TaskAssignedToTeam",
            joinColumns = @JoinColumn(name = "team"),
            inverseJoinColumns = @JoinColumn(name = "task")
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

    public void enrollUser(User user) {teamUser.add(user);} //adds the user we passed in to the set


}
