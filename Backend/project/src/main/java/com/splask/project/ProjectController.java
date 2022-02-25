package com.splask.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    projectDB db;

    @GetMapping("/project/{id}")
    Project getProject(@PathVariable Integer id)
    {
        return db.findById(id).
                orElseThrow(RuntimeException::new);
    }

    @RequestMapping("/projects")
    List<Project> hello(){return db.findAll();}


    @PostMapping("/project")
    Project createProject(@RequestBody Project q) {
        db.save(q);
        return q;
    }

}
