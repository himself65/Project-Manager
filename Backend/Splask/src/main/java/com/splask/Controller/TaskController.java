package com.splask.Controller;

import java.util.List;

import com.splask.Models.Project;
import com.splask.Models.Task;
import com.splask.Repositories.projectDB;
import com.splask.Models.Team;
import com.splask.Repositories.TaskDB;
import com.splask.Repositories.teamDB;

import com.splask.Models.User;
import com.splask.Repositories.UserDB;
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
	
	
//	returns task by id from the database
	@GetMapping("/task/{id}")
	Task getTask(@PathVariable Integer id) {
		return taskRepository.findById(id).get();	
	}

//	returns all the tasks from the database
	@RequestMapping("task")
	List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

//	Creates a new task in the database
	@PostMapping("/task")
	JSONObject createTask(@RequestBody Task newTask) {
		JSONObject responseBody = new JSONObject();
		List<Task> tasks = taskRepository.findAll();

//		Checks if task name already exists
		for (Task t : tasks) {
			if (newTask.getTask().equals(t.getTask())){
				responseBody.put("status", 400);
				responseBody.put("message", "Task Already Exists!");
				return responseBody;
			}
		}

//      Checks length of Task name
		if(newTask.getTask().length() < 1) {
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

//	Delete Task by id from the database
	@DeleteMapping("/task/{id}")
	JSONObject deleteTask(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		taskRepository.deleteById(id);
		responseBody.put("status", 200);
		responseBody.put("message", "Successfully Removed Task #" + id);
		return responseBody;
	}


//	Delete ALL Tasks from the database
	@DeleteMapping("/task")
	JSONObject deleteTasks() {
		JSONObject responseBody = new JSONObject();
		taskRepository.deleteAll();
		responseBody.put("status", 200);
		responseBody.put("message", "Successfully Removed All Tasks " );
		return responseBody;
	}

	
	/**
	 * Sets the task to the assigned user
	 * @param taskID
	 * @param userID
	 * @return JSON Object that hold success or fail statuses and messages
	 */
	@PutMapping("/{task_id}/users/{user_id}")
	Task enrollUserToTask( 
						   @PathVariable Integer taskID,
						   @PathVariable Integer userID
	) {
		Task task = taskRepository.findById(taskID).get();
		User user = userRepository.findById(userID).get();
		task.assignUser(user);
		return taskRepository.save(task); //saves the new task to assigned user;  
	}

	/**
	 * Sets the task to the assigned team
	 * @param taskID
	 * @param teamID
	 * @return JSON Object that hold success or fail statuses and messages
	 */
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

    /**
     * Sets the task to the assigned project
     * @param taskID
     * @param projectID
     * @return  
     */
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

    /**
     * Sets the task to completed 
     * @param task_id
     * @param request
     * @return JSON Object that hold success or fail statuses and messages
     */
	@PutMapping("/task/{task_id}/complete")
	JSONObject setComplete(@PathVariable Integer task_id, @RequestBody JSONObject request)
	{
		JSONObject responseBody = new JSONObject();
		Task task = taskRepository.getById(task_id);
		task.setComplete(userRepository.findByUsername(request.getAsString("username")).get(0));

		taskRepository.save(task);
		responseBody.put("status", 200);
		responseBody.put("message", task.getTask() + " Successfully Completed!");

		return responseBody;
	}




}
