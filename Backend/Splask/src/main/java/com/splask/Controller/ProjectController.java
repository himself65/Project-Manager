package com.splask.Controller;

import com.splask.Models.Project;
import com.splask.Models.Task;
import com.splask.Models.Team;
import com.splask.Repositories.projectDB;
import com.splask.Repositories.teamDB;
import com.splask.Models.User;
import com.splask.Repositories.UserDB;

import com.splask.Repositories.TaskDB;
import net.minidev.json.JSONArray;
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

    @Autowired
    TaskDB taskRepository;


    /**
     *
     * @param id
     * id is an integer that matches a project in the database
     * @return responseBody, if found returns the project object and a success status,
     *         if Failed to find sends a fail message.
     */

    @GetMapping("/project/{id}")
    JSONObject getProject(@PathVariable Integer id)
    {
        JSONObject responseBody = new JSONObject();
        if (!projectRepository.existsById(id))
        {
            responseBody.put("status", 400);
            responseBody.put("message", "Project does not exist");
        }

        responseBody.put("project",projectRepository.findById(id));
        responseBody.put("adminId",projectRepository.getById(id).getAdmin());
        responseBody.put("status", 200);
        responseBody.put("message", "Projects successfully retrieved");

        return responseBody;
    }

    /**
     *
     * @return A JSONObject with a response code and a JSON array contain all
     *         projects in the database.
     */
    @RequestMapping("/project")
    JSONObject getAllProjects(){
        JSONObject responseBody = new JSONObject();
        JSONArray projectsArray = new JSONArray();

        projectsArray.addAll(projectRepository.findAll());

        responseBody.put("projects", projectsArray);
        responseBody.put("status", 200);
        responseBody.put("message", "Project successfully retrieved");

        return responseBody;
    }

    /**
     * Creates a new Project and adds the project into the repository
     * as well as adding the user who created the project
     *
     * @param object
     *        receive a JSONObject from the frontend with data to instantiate
     *        a new project Object
     *
     * @return A success code to tell if the project successfully is created,
     *         A fail code if the project name is already in use.
     */
    @PostMapping("/project")
    JSONObject createProject(@RequestBody JSONObject object){
        JSONObject responseBody = new JSONObject();

        Project newProject = new Project();
        newProject.setProjectName(object.getAsString("projectName"));
        User user = userRepository.findByUsername(object.getAsString("username")).get(0);


        List<Project> projects = projectRepository.findAll();

        for (Project p : projects) {
            if (p.getProjectName().equals(newProject.getProjectName()))
            {
                responseBody.put("status", 400);
                responseBody.put("message", "Project Name in Use");
                return responseBody;
            }
        }
        newProject.enrollUserToProject(userRepository.findByUsername(object.getAsString("username")).get(0));
        user.addProjectToUser(newProject);
        newProject.setAdmin(user.getUserId());
        projectRepository.save(newProject);
        userRepository.save(user);


        responseBody.put("status", 200);
        responseBody.put("message", "Project successfully created!");
        responseBody.put("project_id", newProject.getProjectID());
        return responseBody;
    }

    /**
     *
     * @param id
     * Integer of the project id needing to be deleted.
     * @return responseBody of a successful deletion
     */
    @DeleteMapping("/project/{id}")
    JSONObject deleteProject(@PathVariable Integer id)
    {
        JSONObject responseBody = new JSONObject();
        projectRepository.deleteById(id);

        responseBody.put("status", 200);
        responseBody.put("message", "Successfully Removed Project");

        return responseBody;
    }


    /**
     * retrieves all users in a project
     * @param project_id
     * Integer id of the project
     * @return responseBody containing a JSONArray of users from the project,
     *         and a success code
     */
    @GetMapping("/project/{project_id}/users")
    JSONObject usersInProject(@PathVariable Integer project_id)
    {
        Project project = projectRepository.getById(project_id);
        JSONArray users = new JSONArray();
        JSONObject responseBody = new JSONObject();

        users.addAll(project.getUsers());


        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all users from" + project.getProjectName());
        responseBody.put("users",users);

        return responseBody;
    }


    /**
     * Adds existing users in database to a project by username
     * @param project_id
     * Integer id of the project
     * @param username
     * A JSON object containing a username of a User
     * @return JSON Response body of status codes, success if added
     *         fail code if user is already in project.
     */
    @PutMapping("/project/{project_id}/addUser")
    JSONObject enrollUserToProject( //Gets the user then assigns the user to the project
                              @PathVariable Integer project_id,
                              @RequestBody JSONObject username
    ) {
        JSONObject responseBody = new JSONObject();

        Project project = projectRepository.getById(project_id);
        User user = userRepository.findByUsername(username.getAsString("username")).get(0);


        if (project.getUsers().contains(user))
        {

            responseBody.put("status", 400);
            responseBody.put("message", user.getUsername() +" already in " + project.getProjectName());
            return responseBody;
        }

        project.enrollUserToProject(user); //sends the passed user to the enrollUserToProject method
        user.addProjectToUser(project);
        projectRepository.save(project);
        userRepository.save(user);

        responseBody.put("status", 200);
        responseBody.put("message", "User successfully added to " + project.getProjectName());
        return  responseBody; //saves the new user to assigned project
    }


    /**
     * retrieves all teams from a project
     * @param project_id
     * Integer id of the project
     * @return JSON Response Body that contains JSONArray of teams and a status code
     */
    @GetMapping("/project/{project_id}/teams")
    JSONObject teamsInProject(@PathVariable Integer project_id)
    {
        Project project = projectRepository.getById(project_id);
        JSONArray teams = new JSONArray();
        JSONObject responseBody = new JSONObject();


        teams.addAll(project.getTeams());


        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all teams from " + project.getProjectName());
        responseBody.put("teams",teams);

        return responseBody;
    }


    /**
     * creates a new Team to a project
     * @param project_id
     * Integer id of the Project
     * @param object
     * A JSONObject carrying code that allows a team Object to be instantiated
     *
     * @return responseBody with a success code or fail code if team name is already in use
     *         in the project.
     */
    @PutMapping("/project/{project_id}/addTeam")
    JSONObject addTeamToProject(@PathVariable Integer project_id, @RequestBody JSONObject object)
    {
        JSONObject responseBody = new JSONObject();

        Project project = projectRepository.getById(project_id);
        Team team = new Team();
        team.setTeamName(object.getAsString("teamName"));

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
        responseBody.put("team_id",team.getTeamID());
        return responseBody;
    }

    /**
     * retrieves all tasks a project
     * @param project_id
     * Integer id of the project
     * @return responseBody with a JSONArray containing all Tasks in the project and a success code.
     */
    @GetMapping("/project/{project_id}/tasks")
    JSONObject tasksInProject(@PathVariable Integer project_id)
    {
        Project project = projectRepository.getById(project_id);
        JSONArray tasks = new JSONArray();
        JSONObject responseBody = new JSONObject();

        tasks.addAll(project.getTasks());

        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all tasks from " + project.getProjectName());
        responseBody.put("tasks",tasks);



        return responseBody;
    }

    /**
     * Adds a new Task to a project
     * @param project_id
     * Integer id of the Project
     * @param object
     * A JSONObject carrying code that allows a task Object to be instantiated
     *
     * @return responseBody with a success code or fail code if task already exists or is already in use
     *         in the project.
     */
    @PutMapping("/project/{project_id}/addTask")
    JSONObject addTaskToProject(@PathVariable Integer project_id, @RequestBody JSONObject object)
    {
        JSONObject responseBody = new JSONObject();



        Project project = projectRepository.getById(project_id);


        Task task = new Task();
        task.setTask(object.getAsString("task"));

        Team assignedTeam = teamRepository.getById((Integer) object.getAsNumber("team_id"));

        if (!project.getTeams().contains(assignedTeam))
        {
            responseBody.put("status", 400);
            responseBody.put("message", "Task does not exist");
            return responseBody;
        }



        if (project.addTaskToProject(task)){

            responseBody.put("status",400);
            responseBody.put("message", "Task already exists");
            return responseBody;
        }

        task.assignTaskToProject(project);
        task.assignTaskToTeam(assignedTeam);
        assignedTeam.assignTeamToTask(task);
        for (User i : assignedTeam.getUsers())
        {
            i.addTaskToUser(task);
        }

        taskRepository.save(task);
        projectRepository.save(project);
        teamRepository.save(assignedTeam);


        responseBody.put("status",200);
        responseBody.put("message", "Task successfully created");
        responseBody.put("task_id",task.getId());

        return responseBody;
    }

    @PutMapping("project/{id}/complete")
    JSONObject setComplete(@PathVariable Integer id, @RequestBody JSONObject request)
    {
        JSONObject responseBody = new JSONObject();
        Project project = projectRepository.getById(id);
        project.completeProject();

        projectRepository.save(project);
        responseBody.put("status", 200);
        responseBody.put("message", project.getProjectName() + " Successfully Completed!");

        return responseBody;
    }


}
