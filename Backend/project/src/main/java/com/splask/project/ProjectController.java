package com.splask.project;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {


    projectDB db;

    @GetMapping("/project/{id}")
    public Project getProject(@PathVariable Integer id)
    {
        return db.findById(id).
                orElseThrow(RuntimeException::new);
    }
// returns all Projects from database
    @RequestMapping("/project")
    List<Project> hello(){return db.findAll();}

// creates new project
    @PostMapping("/project")
    public JSONObject createProject(@RequestBody Project q) {
        JSONObject responseBody = new JSONObject();

        List<Project> projects = db.findAll();

        for (Project p : projects) {
            if (p.projectName.equals(q.projectName))
            {
                responseBody.put("status", 400);
                responseBody.put("message", "Project Name in Use");
                return responseBody;
            }
        }

        db.save(q);
        responseBody.put("status", 200);
        responseBody.put("message", "Project successfully created!");
        db.save(q);
        return responseBody;
    }
// deletes project by id
    @DeleteMapping("/project/{id}")
    JSONObject deleteProject(@PathVariable Integer id)
    {
        JSONObject responseBody = new JSONObject();
        db.deleteById(id);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully Removed Project");

        return responseBody;
    }

}
