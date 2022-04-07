package com.splask.user;


import java.util.List;

import com.splask.project.Project;
import com.splask.team.Team;

import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	@RequestMapping("user")
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
        newUser.setFullName(object.getAsString("fullname"));



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

    @PostMapping("/login")
    JSONObject loginUser(@RequestBody User user) {
        JSONObject responseBody = new JSONObject();
        List<User> users = userRepository.findAll();

//      Updates user logged in status
        for (User other : users) {
            if (other.equals(user)) {
                user.setLoggedIn(1);
                responseBody.put("status", 200);
                responseBody.put("message", "Login Successful");
                return responseBody;
            }
        }

        responseBody.put("status", 400);
        responseBody.put("message", "Login Failed");
        return responseBody;
    }

//  Log out call
    @PostMapping("/logout")
    JSONObject logoutUser(@RequestBody User user) {
        JSONObject responseBody = new JSONObject();
        List<User> users = userRepository.findAll();

        for (User other : users) {
            if (other.username.equals(user.username)) {
                user.setLoggedIn(0);
                responseBody.put("status", 200);
                responseBody.put("message", "User Successfully logged out");
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

        responseBody.put("users",projects);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all projects from" + user.getUsername());

        return responseBody;
    }

    @GetMapping("/user/{user_id}/teams")
    JSONObject obtainTeams(@PathVariable Integer user_id)
    {
        User user = userRepository.getById(user_id);
        JSONArray teams = new JSONArray();
        JSONObject responseBody = new JSONObject();

        teams.addAll(user.getTeam());

        responseBody.put("users",teams);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all teams from" + user.getUsername());

        return responseBody;
    }




}
