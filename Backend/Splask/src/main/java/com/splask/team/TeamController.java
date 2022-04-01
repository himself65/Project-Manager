package com.splask.team;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    teamDB db;


    @GetMapping("/team/{id}")
    Team getTeam(@PathVariable Integer id)
    {

        return db.findById(id).orElseThrow(RuntimeException::new);
    }
// returns all teams in database
    @RequestMapping("/team")
    List<Team> getAllTeams(){

        return db.findAll();}


    @PostMapping("/team")
    public JSONObject createTeam(@RequestBody Team t) {
        JSONObject responseBody = new JSONObject();
        List<Team> teams = db.findAll();

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
        db.save(t);
        return responseBody;

    }

}
