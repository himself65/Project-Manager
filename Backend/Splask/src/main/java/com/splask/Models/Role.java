package com.splask.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleID;

    @Column
    private String roleName;

    @Column
    LocalDateTime dateCreated;

    @ManyToMany(mappedBy = "roles")
    @NotNull
    @JsonIgnore
    private List<User> users = new ArrayList<>();


    public Role() {

        dateCreated = LocalDateTime.now();
    }


    public Integer getRoleID(){return roleID;}

    public void setRoleName(String roleName) {this.roleName = roleName;}
    public String getRoleName(){return  roleName;}

    /**
     * formats the date that was made on instantiated
     * @return a formatted date
     */
    public String getDateCreated()
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateCreated.format(format);
    }

//  Relationships functions
    /**
     * Adds new user to Role
     * @param user
     *
     */
    public void enrollUserToRole(User user) {users.add(user);}

    /**
     *
     * @return list of users in role
     */
    public List<User> getUsers() {return users;}
    public void setUsers(List<User> users) {this.users = users;}








}
