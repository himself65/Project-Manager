package com.splask.project;

import com.splask.team.Team;
import com.splask.team.teamDB;
import com.splask.user.UserDB;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ProjectController {

    @Autowired
    projectDB pDB;

    @Autowired
    teamDB tDB;

    @Autowired
    UserDB uDB;

    @GetMapping("/project/{id}")
    public Project getProject(@PathVariable Integer id)
    {
        return pDB.findById(id).
                orElseThrow(RuntimeException::new);
    }
// returns all Projects from database
    @RequestMapping("/project")
    List<Project> hello(){return pDB.findAll();}

// creates new project
    @PostMapping("/project")
    public JSONObject createProject(@RequestBody Project q) {
        JSONObject responseBody = new JSONObject();

        List<Project> projects = pDB.findAll();

        for (Project p : projects) {
            if (p.projectName.equals(q.projectName))
            {
                responseBody.put("status", 400);
                responseBody.put("message", "Project Name in Use");
                return responseBody;
            }
        }

        pDB.save(q);
        responseBody.put("status", 200);
        responseBody.put("message", "Project successfully created!");
        return responseBody;
    }

    /*
    TODO
    @PutMapping("/project/team")
    public JSONObject addTeam(@RequestBody Project p, Team t) {
        JSONObject responseBody = new JSONObject();

        for (Team i : p.teams)
        {
            if (t.getTeamName().equals(i.getTeamName())) {
                responseBody.put("status", 400);
                responseBody.put("message", i.getTeamName() + "already in Project");
                return responseBody;
            }
        }
        p.teams.add(t);
        responseBody.put("status",400);
        responseBody.put("message", "Team successfully added");

        return responseBody;
    }
    */

// deletes project by id
    @DeleteMapping("/project/{id}")
    JSONObject deleteProject(@PathVariable Integer id)
    {
        JSONObject responseBody = new JSONObject();
        pDB.deleteById(id);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully Removed Project");

        return responseBody;
    }

}
