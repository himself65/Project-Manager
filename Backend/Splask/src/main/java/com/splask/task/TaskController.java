package com.splask.task;

import java.util.List;

import com.splask.project.Project;
import com.splask.project.projectDB;
import com.splask.team.Team;
import com.splask.team.teamDB;
import com.splask.team.Team;

import com.splask.user.User;
import com.splask.user.UserDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import net.minidev.json.JSONObject;


@RestController 
public class TaskController {
	

	@Autowired
	TaskDB taskRepository;

	@Autowired
	UserDB userRepository;

	@Autowired
	teamDB teamRepository;

	@Autowired
	projectDB projectRepository;
	
//	Get task by id
	@GetMapping("/task/{id}")  
	Task getTask(@PathVariable Integer id) {
		return taskRepository.findById(id).get();	
	}

//  Get all the tasks
	@RequestMapping("task")
	List<Task> getAllTasks() {
		return taskRepository.findAll();
	}
	
//	Creates a new task
	@PostMapping("/task")
	public JSONObject createTask(@RequestBody Task newTask) {
		JSONObject responseBody = new JSONObject();
		List<Task> tasks = taskRepository.findAll();

//		Checks if task name already exists
		for (Task t : tasks) {
			if (newTask.taskName.equals(newTask.taskName)){
				responseBody.put("status", 400);
				responseBody.put("message", "Task Already Exists!");
				return responseBody;
			}
		}

//      Checks length of Task name
		if(newTask.taskName.length() < 1) {
			responseBody.put("status", 400);
			responseBody.put("message", "Task Name Too Short!");
			return responseBody;
		}

//		saves the new task
		taskRepository.save(newTask);
		responseBody.put("status", 200);
		responseBody.put("message", "Task Successfully Created!");
		return responseBody;
	}

	// Delete Task by id
	@DeleteMapping("/task/{id}")
	JSONObject deleteTask(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		taskRepository.deleteById(id);
		responseBody.put("status", 200);
		responseBody.put("message", "Successfully Removed Task #" + id);
		return responseBody;
	}

//	Delete ALL Tasks
	@DeleteMapping("/task")
	JSONObject deleteTasks() {
		JSONObject responseBody = new JSONObject();
		taskRepository.deleteAll();
		responseBody.put("status", 200);
		responseBody.put("message", "Successfully Removed All Tasks " );
		return responseBody;
	}

	
	
//	TODO Waiting to be tested
	@PutMapping("/{task_id}/users/{user_id}")
	Task enrollUserToTask( //Gets the user then assigns the user to the task
						   @PathVariable Integer taskID, /*TODO if error, change variable name to task_id and test */
						   @PathVariable Integer userID
	) {
		Task task = taskRepository.findById(taskID).get();
		User user = userRepository.findById(userID).get();
		task.assignUser(user); //sends the passed user to the assignUser method
		return  taskRepository.save(task); //saves the new task to assigned user
	}
//  TODO Waiting to be tested
//  Sets the task to the assigned team
    @PutMapping("/task/{task_id}/add")
    JSONObject assignTaskToTeam(
    		@PathVariable Integer taskID,
    		@PathVariable Integer teamID
    ) {
		JSONObject responseBody = new JSONObject();

		Task task = taskRepository.getById(taskID);
		Team team = teamRepository.getById(teamID);
		if(team.getTasks().contains(task))
		{
			responseBody.put("status", 400);
			responseBody.put("message", "task is already assigned to this team");

			return responseBody;
		}

    	task.assignTaskToTeam(team);
		taskRepository.save(task);
		responseBody.put("status",200);
		responseBody.put("message", "task successfully added to" + team.getTeamName());
    	return responseBody;
    }


//  TODO Waiting to be tested
//  Sets the task to the assigned project
    @PutMapping("/task/{task_id}/project/{project_id}")
    Task assignTaskToProject(
    		@PathVariable Integer taskID,
    		@PathVariable Integer projectID
    ) {
    	Task task= taskRepository.getById(taskID);
    	Project project = projectRepository.getById(projectID);
    	task.assignTaskToProject(project);
    	return taskRepository.save(task);
    }




}
