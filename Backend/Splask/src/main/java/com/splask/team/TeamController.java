package com.splask.team;

import com.splask.project.Project;
import com.splask.project.projectDB;
import com.splask.task.Task;
import com.splask.task.TaskDB;
import com.splask.user.User;
import com.splask.user.UserDB;


import net.minidev.json.JSONArray;
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

    //method used for testing a creation of a class
    @PostMapping("/team")
    JSONObject createTeam(@RequestBody Team t) {
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

    /**
     * retreives all users in a team
     * @param team_id
     * @return responseBody with a JSON Array of users in a team
     */
    @GetMapping("/team/{team_id}/users")
    JSONObject usersInTeam(@PathVariable Integer team_id)
    {
        Team team= teamRepository.getById(team_id);
        JSONArray users = new JSONArray();
        JSONObject responseBody = new JSONObject();

        users.addAll(team.getUsers());
        responseBody.put("users",users);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all teams from" + team.getTeamName());


        return responseBody;
    }

    /**
     * Adds a new user to a team in the project
     * @param teamID
     * @param username
     * @return JSON Object that hold success or fail statuses and messages
     */
    @PutMapping("/team/{teamID}/addUser")
    JSONObject enrollUserToTeam( //Gets the user then assigns the user to the team
                              @PathVariable Integer teamID,
                              @RequestBody JSONObject username
    ) {
        JSONObject responseBody = new JSONObject();

        Team team = teamRepository.getById(teamID);
        User user = userRepository.findByUsername(username.getAsString("username")).get(0);

        if (!team.getTeamProject().getUsers().contains(user)) {

            responseBody.put("status",400);
            responseBody.put("message","Invalid. User not in project");
            return responseBody;
        }
        if (team.getUsers().contains(user))
        {
            responseBody.put("status",400);
            responseBody.put("message", "User already in Team");
            return responseBody;
        }
        for(Task teamTasks : team.getTasks())
        {
            if(!user.getTasks().contains(teamTasks))
            {
                user.addTaskToUser(teamTasks);
            }
        }

        team.enrollUser(user); //sends the passed user to the enrollUsers method
        user.addTeamToUser(team);
        teamRepository.save(team);
        userRepository.save(user);
        responseBody.put("status",200);
        responseBody.put("message", "User successfully added to Team");

        return  responseBody;
    }

    /**
     * Testing method of assigning a project to a team
     * @param projectID
     * @param teamID
     * @return
     */
    @PutMapping("/team/{team_id}/project/addProject")
    Team assignTaskToTeam(
    		@PathVariable Integer projectID,
    		@PathVariable Integer teamID

    ) {
    	Project project = projectRepository.getById(projectID);
    	Team team= teamRepository.getById(teamID);
    	team.assignTeamToProject(project);
    	return teamRepository.save(team);
    }

    /**
     * retrieves all tasks that is assigned in a team
     * @param team_id
     * @return JSON responsebody containing status codes and a JSON array of tasks
     */
    @GetMapping("/team/{team_id}/tasks")
    JSONObject tasksInTeam(@PathVariable Integer team_id)
    {
        Team team = teamRepository.getById(team_id);
        JSONArray tasks = new JSONArray();
        JSONObject responseBody = new JSONObject();

        tasks.addAll(team.getTasks());

        responseBody.put("tasks",tasks);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all tasks from" + team.getTeamName());



        return responseBody;
    }


    // deletes project by id
    @DeleteMapping("/team/{id}")
    JSONObject deleteTeam(@PathVariable Integer id)
    {
        JSONObject responseBody = new JSONObject();
        teamRepository.deleteById(id);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully Removed Team");

        return responseBody;
    }
    

}
