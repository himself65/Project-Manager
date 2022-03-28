package com.splask.team;

import com.splask.user.User;
import com.splask.user.UserDB;

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
        return teamRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @RequestMapping("/team")
    List<Team> getAllTeams(){return teamRepository.findAll();}


    @PostMapping("/team")
    Team createTeam(@RequestBody Team t) {
        teamRepository.save(t);
        return t;
    }

    @PostMapping("/{team_id}/users/{user_id}")
    Team enrollUserToSubject( //Gets the user then assigns the user to the subject
                              @PathVariable Integer teamID,
                              @PathVariable Integer userID
    ) {
        Team team = teamRepository.findById(teamID).get();
        User user = userRepository.findById(userID).get();
        team.enrollUsers(user); //sends the passed user to the enrollUsers method
        return  teamRepository.save(team); //saves the new user to assigned team
    }

    
    
    
    
    
    
    
    
    
    

}
