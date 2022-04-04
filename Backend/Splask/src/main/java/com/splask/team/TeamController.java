package com.splask.team;

import com.splask.project.Project;
import com.splask.project.projectDB;
import com.splask.task.TaskDB;
import com.splask.user.User;
import com.splask.user.UserDB;


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
    
    //TODO Waiting to be tested 
//  Sets the user to the assigned team
    @PutMapping("/team/{team_id}/user/addUser")
    JSONObject enrollUserToTeam( //Gets the user then assigns the user to the team
                              @PathVariable Integer teamID,
                              @PathVariable Integer userID
    ) {
        JSONObject responseBody = new JSONObject();

        Team team = teamRepository.getById(teamID);
        User user = userRepository.getById(userID);

        if (team.getttUsers().contains(user))
        {
            responseBody.put("status",400);
            responseBody.put("message", "User already in Team");
            return responseBody;
        }


        team.enrollUser(user); //sends the passed user to the enrollUsers method
        teamRepository.save(team);
        responseBody.put("status",200);
        responseBody.put("message", "User successfully added to team");

        return  responseBody;
    }
    
    //TODO Waiting to be tested 
    @PutMapping("/team/{team_id}/project/addProject")
    Team assignTaskToTeam(
    		@PathVariable Integer projectID,
    		@PathVariable Integer teamID
    		
    ) {
    	Project project = projectRepository.findById(projectID).get();
    	Team team= teamRepository.findById(teamID).get();
    	team.assignTeamToProject(project);
    	return teamRepository.save(team);
    }
    


    
    
    
    
    
    
    
    
    

}
