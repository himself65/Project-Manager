package com.splask.team;

import com.splask.project.Project;
import com.splask.user.User;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;



@Entity
public
class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    Integer teamID;

    @Column
    String teamName;

    @Column
    String teamIntro;

    @Column
    String teamUsers;

    @ManyToOne
    @JoinColumn(name = "projectID")
    @JsonIgnore
    Project teamProjects;

    @OneToMany
//  Creates new join table with colum of user_id and team_id and
//  creates relationship between them. Relationship Team to User
    @JoinTable(
            name = "users_enrolled" ,
            joinColum = @JoinColumns(name = "user_id"),
            inverseJoinColumns = @JoinColumns(name = "team_id")
    )
    private Set<User> enrrolledUsers = new HashSet<>();


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

    public Project getTeamProjects() {return teamProjects;}

    public void enrollUsers(User user) {
        enrrolledUsers.add(user); //adds the student we passed in to the set
    }


}
