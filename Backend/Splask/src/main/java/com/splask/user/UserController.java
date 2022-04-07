package com.splask.user;


import java.util.List;

import com.splask.project.Project;
import com.splask.team.Team;

import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import com.sun.org.apache.xerces.internal.util.URI;

import net.minidev.json.JSONObject;

@RestController 
public class UserController {
	
    @Autowired
	UserDB userRepository;

//  get user by ID
	@GetMapping("/user/{id}")
	User getUsername(@PathVariable Integer id) {
		return userRepository.findById(id).orElseThrow(RuntimeException::new);
	}

//  get all the users
	@RequestMapping("/user")
	List<User> getAllUsers() {
		return userRepository.findAll();
	}

// Registers user to the database and checks if there is an
// existing user registered with the same username
	@PostMapping("/register")
	JSONObject registerUser(@RequestBody JSONObject object) {
		JSONObject responseBody = new JSONObject();

        User newUser = new User();

        newUser.setUsername(object.getAsString("username"));
        newUser.setUserPassword(object.getAsString("userPassword"));
        newUser.setFullName(object.getAsString("full_name"));


        List<User> users = userRepository.findAll();


        for (User user : users) {
            if (user.username.equals(newUser.username)) {
                responseBody.put("status", 400);
                responseBody.put("message", "User Already Exists!");
                return responseBody;
            }
        }

        if(newUser.password.length() < 3){
            responseBody.put("status", 400);
            responseBody.put("message", "Password should be at least 3 characters long");
            return responseBody;
        }

//      Checks length, upper, lower case, numeric value and special character of the input password

//        if (!newUser.isAllPresent(newUser.password)){ //Checks if the password meets the safety criteria
//            responseBody.put("status", 400);
//            responseBody.put("message", "Please enter a valid password of at least 4 characters containing uppercase, lowercase\n" +
//                    "\t// special character & numeric value");
//        }

        userRepository.save(newUser);
        responseBody.put("status", 200);
        responseBody.put("message", "Account successfully created!");
        
        return responseBody;
    }

    @PutMapping("/login")
    JSONObject loginUser(@RequestBody User user) {
        JSONObject responseBody = new JSONObject();
        List<User> users = userRepository.findAll();
//      Updates user logged in status
        for (User userInDB : users) {

            if (userInDB.equals(user)) {
                userInDB.setLoggedIn(1);

                responseBody.put("user_id", userInDB.getUserId());
                responseBody.put("fullname", userInDB.getFullName());
                responseBody.put("username", userInDB.getUsername());
                responseBody.put("status", 200);
                responseBody.put("message", "Login Successful");
              userRepository.save(userInDB);
                return responseBody;
            }
        }


        responseBody.put("status", 400);
        responseBody.put("message", "Login Failed");
        return responseBody;
    }

//  Log out call
    @PutMapping("/logout")
    JSONObject logoutUser(@RequestBody User user) {
        JSONObject responseBody = new JSONObject();
        List<User> users = userRepository.findAll();

        for (User userInDB : users) {
            if (userInDB.getUsername().equals(user.getUsername())) {

                userInDB.setLoggedIn(0);

                responseBody.put("status", 200);
                responseBody.put("message", "User Successfully logged out");

                userRepository.save(userInDB);
                return responseBody;
            }
        }
        responseBody.put("status", 400);
        responseBody.put("message", "Failure to logout");
        return responseBody;
    }

//	 Delete user by id
	@DeleteMapping("/user/{id}")
	JSONObject deleteUser(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();
		userRepository.deleteById(id);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully deleted user");
		return responseBody;
	}

//	Delete ALL users
    @DeleteMapping("/user/all")
    JSONObject deleteUsers() {
        JSONObject responseBody = new JSONObject();
    	userRepository.deleteAll();
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully deleted all users");
        return responseBody;
    }
    @GetMapping("/user/{user_id}/projects")
    JSONObject obtainProjects(@PathVariable Integer user_id)
    {
        User user = userRepository.getById(user_id);
        JSONArray projects = new JSONArray();
        JSONObject responseBody = new JSONObject();

        projects.addAll(user.getProject());


        responseBody.put("projects",projects);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all projects from " + user.getUsername());

        return responseBody;
    }

    @GetMapping("/user/{user_id}/teams")
    JSONObject obtainTeams(@PathVariable Integer user_id)
    {
        User user = userRepository.getById(user_id);
        JSONArray teams = new JSONArray();
        JSONObject responseBody = new JSONObject();

        teams.addAll(user.getTeam());

        responseBody.put("teams",teams);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all teams from " + user.getUsername());

        return responseBody;
    }

    @GetMapping("/user/{user_id}/tasks")
    JSONObject obtainTasks(@PathVariable Integer user_id)
    {
        User user = userRepository.getById(user_id);
        JSONArray tasks = new JSONArray();
        JSONObject responseBody = new JSONObject();

        tasks.addAll(user.getTasks());

        responseBody.put("tasks",tasks);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all teams from" + user.getUsername());

        return responseBody;
    }




}
