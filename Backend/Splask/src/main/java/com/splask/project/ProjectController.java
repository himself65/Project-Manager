package com.splask.project;

import com.splask.task.Task;
import com.splask.team.Team;
import com.splask.team.teamDB;
import com.splask.user.User;
import com.splask.user.UserDB;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProjectController {

    @Autowired
    projectDB projectRepository;

    @Autowired
    teamDB teamRepository;

    @Autowired
    UserDB userRepository;

    @GetMapping("/project/{id}")
    public Project getProject(@PathVariable Integer id)
    {
        return projectRepository.findById(id).
                orElseThrow(RuntimeException::new);
    }
// returns all Projects from database
    @RequestMapping("/project")
    List<Project> hello(){return projectRepository.findAll();}

// creates new project
    @PostMapping("/project")
    public JSONObject createProject(@RequestBody Project q) {
        JSONObject responseBody = new JSONObject();

        List<Project> projects = projectRepository.findAll();

        for (Project p : projects) {
            if (p.projectName.equals(q.projectName))
            {
                responseBody.put("status", 400);
                responseBody.put("message", "Project Name in Use");
                return responseBody;
            }
        }

        projectRepository.save(q);
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
        projectRepository.deleteById(id);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully Removed Project");

        return responseBody;
    }
    
    
    //TODO Waiting to be tested 
//  Sets the user to the assigned project
    @PutMapping("/project/{project_id}/user/{user_id}")
    Project enrollUserToProject( //Gets the user then assigns the user to the project
                              @PathVariable Integer projectID,
                              @PathVariable Integer userID
    ) {
        Project project = projectRepository.findById(projectID).get();
        User user = userRepository.findById(userID).get();
        project.enrollUserToProject(user); //sends the passed user to the enrollUsers method
        return  projectRepository.save(project); //saves the new user to assigned team
    }

}
