package com.splask.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    teamDB db;


    @GetMapping("/project/{id}")
    Team getTeam(@PathVariable Integer id)
    {
        return db.findById(id).orElseThrow(RuntimeException::new);
    }

    @RequestMapping("/projects")
    List<Team> hello(){return db.findAll();}


    @PostMapping("/project")
    Team createTeam(@RequestBody Team t) {
        db.save(t);
        return t;
    }

}
