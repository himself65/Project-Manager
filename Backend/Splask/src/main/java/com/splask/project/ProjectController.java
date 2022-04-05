package com.splask.project;

import com.splask.task.Task;
import com.splask.team.Team;
import com.splask.team.TeamController;
import com.splask.team.teamDB;
import com.splask.user.User;
import com.splask.user.UserDB;

import com.splask.task.TaskDB;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;


@RestController
public class ProjectController {

    @Autowired
    projectDB projectRepository;

    @Autowired
    teamDB teamRepository;

    @Autowired
    UserDB userRepository;

    @Autowired
    TaskDB taskRepository;


    @GetMapping("/project/{id}")
    //should be JSONObject
    public Project getProject(@PathVariable Integer id)
    {
        return projectRepository.findById(id).
                orElseThrow(RuntimeException::new);
    }
// returns all Projects from database
    //should be JSONObject
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
    


    // returns all users in the project
    @GetMapping("/project/{project_id}/users")
    JSONObject usersInProject(@PathVariable Integer project_id)
    {
        Project project = projectRepository.getById(project_id);
        JSONArray users = new JSONArray();
        JSONObject responseBody = new JSONObject();
        /*
        for (User i : project.getUsers())
        {
            users.add(i);
        }
         */
        users.addAll(project.getUsers());
        responseBody.put("users",users);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all users from" + project.getProjectName());

        return responseBody;
    }

    //TODO Waiting to be tested
//  Sets the user to the assigned project
    @PutMapping("/project/{project_id}/addUser")
    JSONObject enrollUserToProject( //Gets the user then assigns the user to the project
                              @PathVariable Integer project_id,
                              @RequestBody JSONObject username
    ) {
        JSONObject responseBody = new JSONObject();

        Project project = projectRepository.getById(project_id);
        List<User> users = userRepository.findByUsername(username.getAsString("username"));
        System.out.println(username.toString());
        System.out.println(users.size());
        User user = users.get(0);
        if (project.getUsers().contains(user))
        {
            responseBody.put("status", 400);
            responseBody.put("message", user.getUsername() +"already in" + project.getProjectName());
            return responseBody;
        }
        project.enrollUserToProject(user); //sends the passed user to the enrollUsers method
        user.addProjectToUser(project);
        responseBody.put("status", 200);
        responseBody.put("message", "User successfully added to" + project.getProjectName());
        return  responseBody; //saves the new user to assigned team
    }


    //retrieves all teams from Project
    @GetMapping("/project/{project_id}/teams")
    JSONObject teamsInProject(@PathVariable Integer project_id)
    {
        Project project = projectRepository.getById(project_id);
        JSONArray teams = new JSONArray();
        JSONObject responseBody = new JSONObject();
        /*
        for (User i : project.getUsers())
        {
            users.add(i);
        }
         */
        teams.addAll(project.getTeams());
        responseBody.put("teams",teams);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all teams from " + project.getProjectName());

        return responseBody;
    }
    @PutMapping("/project/{project_id}/addTeam")
    JSONObject addTeamToProject(@PathVariable Integer project_id, @RequestBody Team team)
    {
        JSONObject responseBody = new JSONObject();

        Project project = projectRepository.getById(project_id);

        if (project.addTeamToProject(team)){
            responseBody.put("status",400);
            responseBody.put("message", "Team name already in use");
            return responseBody;
        }
        team.assignTeamToProject(project);
        projectRepository.save(project);
        teamRepository.save(team);



        responseBody.put("status",200);
        responseBody.put("message", "Team successfully created");
        return responseBody;
    }

    //retrieves all teams from Project
    @GetMapping("/project/{project_id}/tasks")
    JSONObject tasksInProject(@PathVariable Integer project_id)
    {
        Project project = projectRepository.getById(project_id);
        JSONArray tasks = new JSONArray();
        JSONObject responseBody = new JSONObject();
        /*
        for (User i : project.getUsers())
        {
            users.add(i);
        }
         */
        tasks.addAll(project.getTasks());

        responseBody.put("tasks",tasks);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all tasks from" + project.getProjectName());



        return responseBody;
    }

    @PutMapping("/project/{project_id}/addTask")
    JSONObject addTaskToProject(@PathVariable Integer project_id)
    {
        JSONObject responseBody = new JSONObject();
        Task task = new Task();

        Project project = projectRepository.getById(project_id);
        project.addTaskToProject(task);
        taskRepository.save(task);
        projectRepository.save(project);
        responseBody.put("status",200);
        responseBody.put("message", "Task successfully created");

        return responseBody;
    }


}
