package com.splask.team;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    teamDB teamRepository;

    @Autowired
    UserDB userRepository;

    @GetMapping("/team/{id}")
    Team getTeam(@PathVariable Integer id)
    {

        return teamDB.findById(id).orElseThrow(RuntimeException::new);
    }
// returns all teams in database
    @RequestMapping("/team")
    List<Team> getAllTeams(){

        return teamDB.findAll();}


    @PostMapping("/team")
    public JSONObject createTeam(@RequestBody Team t) {
        JSONObject responseBody = new JSONObject();
        List<Team> teams = teamDB.findAll();

        for (Team i : teams)
        {
            if (t.getTeamName().equals(i.getTeamName()))
            {
                responseBody.put("status", 400);
                responseBody.put("message", "Project Name in Use");
                return responseBody;
            }
        }

        responseBody.put("status", 200);
        responseBody.put("message", "Team successfully created");
        teamDB.save(t);
        return responseBody;

    }

    @PutMapping("/team/{team_id}/user/{user_id}")
    Team enrollUserToTeam( //Gets the user then assigns the user to the team
                              @PathVariable Integer teamID,
                              @PathVariable Integer userID
    ) {
        Team team = teamRepository.findById(teamID).get();
        User user = userRepository.findById(userID).get();
        team.enrollUser(user); //sends the passed user to the enrollUsers method
        return  teamRepository.save(team); //saves the new user to assigned team
    }

    
    
    
    
    
    
    
    
    
    

}
