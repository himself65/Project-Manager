package com.splask.Controller;

import com.splask.Models.Project;
import com.splask.Models.Team;
import com.splask.Repositories.projectDB;
import com.splask.Models.Task;
import com.splask.Repositories.TaskDB;
import com.splask.Models.User;
import com.splask.Repositories.teamDB;
import com.splask.Repositories.UserDB;


import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    teamDB teamRepository;
    
    @Autowired
    UserDB userRepository;

    @Autowired
    TaskDB taskRepository;

    @Autowired
    projectDB projectRepository;

    @GetMapping("/team/{id}")
    Team getTeam(@PathVariable Integer id)
    {

        return teamRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    /**
     *
     * @return responseBody with a JSONArray containing all teams in the DB
     *         and Success code
     */
    @RequestMapping("/team")
    JSONObject getAllTeams(){
        JSONObject responseBody = new JSONObject();
        JSONArray teamArray = new JSONArray();

        teamArray.addAll(teamRepository.findAll());

        responseBody.put("teams", teamArray);
        responseBody.put("status", 200);
        responseBody.put("message", "Project successfully retrieved");
        return responseBody;
    }

    /**
     * Creates a new Team without being tied to a project
     * used for mostly testing
     *
     * @param object
     * JSONObject that holds keys to instantiate a new Team object
     *
     * @return responseBody, A success code if team is successfully created,
     *         fail code if team already exists in the database.
     */
    @PostMapping("/team")
    JSONObject createTeam(@RequestBody JSONObject object) {
        JSONObject responseBody = new JSONObject();
        List<Team> teams = teamRepository.findAll();
        Team newTeam = new Team();
        newTeam.setTeamName(object.getAsString("teamName"));

        for (Team i : teams)
        {
            if (newTeam.getTeamName().equals(i.getTeamName()))
            {
                responseBody.put("status", 400);
                responseBody.put("message", "Team Name in Use");
                return responseBody;
            }
        }

        responseBody.put("status", 200);
        responseBody.put("message", "Team successfully created");
        teamRepository.save(newTeam);
        return responseBody;

    }

    /**
     * retrieves all users in a team
     *
     * @param team_id
     * Integer id of the Team
     *
     * @return responseBody with a JSON Array of users in a team and a success code
     */
    @GetMapping("/team/{team_id}/users")
    JSONObject usersInTeam(@PathVariable Integer team_id)
    {
        Team team= teamRepository.getById(team_id);
        JSONArray users = new JSONArray();
        JSONObject responseBody = new JSONObject();

        users.addAll(team.getUsers());

        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all teams from" + team.getTeamName());
        responseBody.put("users",users);


        return responseBody;
    }

    /**
     * Adds a new user to a team in the project
     * @param teamID
     * Integer id of the team
     * @param username
     * JSONObject containing a username of the user wanting to be added to the team.
     *
     * @return JSON Object that hold success code when user is added in the project,
     *         fail codes if user is not in project or if user is in the team already.
     */
    @PutMapping("/team/{teamID}/addUser")
    JSONObject enrollUserToTeam( //Gets the user then assigns the user to the team
                              @PathVariable Integer teamID,
                              @RequestBody JSONObject username
    ) {
        JSONObject responseBody = new JSONObject();

        Team team = teamRepository.getById(teamID);
        User user = userRepository.findByUsername(username.getAsString("username")).get(0);

        if (!team.getTeamProject().getUsers().contains(user)) {

            responseBody.put("status",400);
            responseBody.put("message","Invalid. User not in project");
            return responseBody;
        }
        if (team.getUsers().contains(user))
        {
            responseBody.put("status",400);
            responseBody.put("message", "User already in Team");
            return responseBody;
        }
        for(Task teamTasks : team.getTasks())
        {
            if(!user.getTasks().contains(teamTasks))
            {
                user.addTaskToUser(teamTasks);
            }
        }

        team.enrollUser(user); //sends the passed user to the enrollUsers method
        user.addTeamToUser(team);
        teamRepository.save(team);
        userRepository.save(user);

        responseBody.put("status",200);
        responseBody.put("message", "User successfully added to Team");
        responseBody.put("user",user);

        return  responseBody;
    }

    /**
     * Testing method of assigning a project to a team
     *
     * @param teamID
     * Integer id of the Team
     * @param object
     * A JSONObject holding the id of a Project
     * @return responseBody with a success code
     */
//    @PutMapping("/team/{team_id}/project/addProject")
//    JSONObject assignTaskToTeam(
//    		@PathVariable Integer teamID,
//    		@RequestBody JSONObject object
//
//    ) {
//
//        JSONObject responseBody = new JSONObject();
//    	Project project = projectRepository.getById((Integer) object.getAsNumber("projectID"));
//    	Team team= teamRepository.getById(teamID);
//    	team.assignTeamToProject(project);
//
//        responseBody.put("status", 200);
//        responseBody.put("message", "Successfully assigned a Project to a Team");
//    	return responseBody;
//    }

    /**
     * retrieves all tasks that are assigned in a team
     * @param team_id
     * Integer id of the Team
     * @return JSON responseBody containing status codes and a JSON array of tasks
     */
    @GetMapping("/team/{team_id}/tasks")
    JSONObject tasksInTeam(@PathVariable Integer team_id)
    {
        Team team = teamRepository.getById(team_id);
        JSONArray tasks = new JSONArray();
        JSONObject responseBody = new JSONObject();

        tasks.addAll(team.getTasks());


        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all tasks from" + team.getTeamName());
        responseBody.put("tasks",tasks);



        return responseBody;
    }


    /**
     *
     * @param id
     * Integer id of the team needed to be deleted
     * @return responseBody of a successful deletion
     */
    @DeleteMapping("/team/{id}")
    JSONObject deleteTeam(@PathVariable Integer id)
    {
        JSONObject responseBody = new JSONObject();
        teamRepository.deleteById(id);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully Removed Team");

        return responseBody;
    }
    

}
