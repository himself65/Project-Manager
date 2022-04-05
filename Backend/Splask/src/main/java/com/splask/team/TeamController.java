package com.splask.team;

import com.splask.project.Project;
import com.splask.project.projectDB;
import com.splask.task.TaskDB;
import com.splask.user.User;
import com.splask.user.UserDB;


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
// returns all teams in database
    @RequestMapping("/team")
    List<Team> getAllTeams(){

        return teamRepository.findAll();}


    @PostMapping("/team")
    public JSONObject createTeam(@RequestBody Team t) {
        JSONObject responseBody = new JSONObject();
        List<Team> teams = teamRepository.findAll();

        for (Team i : teams)
        {
            if (t.getTeamName().equals(i.getTeamName()))
            {
                responseBody.put("status", 400);
                responseBody.put("message", "Team Name in Use");
                return responseBody;
            }
        }

        responseBody.put("status", 200);
        responseBody.put("message", "Team successfully created");
        teamRepository.save(t);
        return responseBody;

    }
    

    @GetMapping("/team/{team_id}/users")
    JSONObject usersInTeam(@PathVariable Integer teamID)
    {
        Team team= teamRepository.getById(teamID);
        JSONArray users = new JSONArray();
        JSONObject responseBody = new JSONObject();

        users.addAll(team.getUsers());
        responseBody.put("users",users);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all teams from" + team.getTeamName());


        return responseBody;
    }
//  Sets the user to the assigned team
    @PutMapping("/team/{team_id}/addUser")
    JSONObject enrollUserToTeam( //Gets the user then assigns the user to the team
                              @PathVariable Integer teamID,
                              @PathVariable Integer userID
    ) {
        JSONObject responseBody = new JSONObject();

        Team team = teamRepository.getById(teamID);
        User user = userRepository.getById(userID);

        if (team.getUsers().contains(user))
        {
            responseBody.put("status",400);
            responseBody.put("message", "User already in Team");
            return responseBody;
        }


        team.enrollUser(user); //sends the passed user to the enrollUsers method
        teamRepository.save(team);
        responseBody.put("status",200);
        responseBody.put("message", "User successfully added to Team");

        return  responseBody;
    }

    //TODO Waiting to be tested
    @PutMapping("/team/{team_id}/project/addProject")
    Team assignTaskToTeam(
    		@PathVariable Integer projectID,
    		@PathVariable Integer teamID

    ) {
    	Project project = projectRepository.getById(projectID);
    	Team team= teamRepository.getById(teamID);
    	team.assignTeamToProject(project);
    	return teamRepository.save(team);
    }


    // deletes project by id
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
