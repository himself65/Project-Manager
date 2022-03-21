package com.splask.team;

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

    @RequestMapping("/team")
    List<Team> getAllTeams(){return db.findAll();}


    @PostMapping("/team")
    Team createTeam(@RequestBody Team t) {
        db.save(t);
        return t;
    }

}
