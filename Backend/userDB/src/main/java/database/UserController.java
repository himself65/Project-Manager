package database;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.*;

import javax.validation.Valid;

import database.User;
import net.minidev.json.JSONObject;

@RestController 
public class UserController {
	
	@Autowired
	UserDB userRepository;

	/*
	 * to get User details by ID from the database
	 */
	@GetMapping("/user/{id}")  //("/user/{id}") is the endpoint 
	User getUserName(@PathVariable Integer id) {
		return userRepository.findById(id).get();	
	}
	
/*
 * 	to get all the Users details from the database
 */
	@RequestMapping("/user")
	List<User> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}
	
/*
 * Registers user to the database and checks if there is an
 * existing user registered with the same parameters 
 */
	@PostMapping("/register")
	public JSONObject registerUser(@Valid @RequestBody User newUser) {
		
		JSONObject responseBody = new JSONObject();
		List<User> users = userRepository.findAll();

        System.out.println("New user: " + newUser.toString());
        
        for (User user : users) {
            if (user.username.equals(newUser.username)) {
                System.out.println("User Already exists!");
                responseBody.put("status", 400);
                responseBody.put("message", "User Already Exists!");
                return responseBody;
            }
        }
	/*
	 * Checks the length of the password        
	 */
        if(newUser.password.length() < 3) {
            responseBody.put("status", 400);
            responseBody.put("message", "Password Too Short!");
            return responseBody;
        }
        /*
         * Check that the password contains an upper, lower, 
         * & a numeric value  and that the password is not null
         */
        if((newUser.password != null) && (checkString(newUser.password) == true)) {  
        	        	
            System.out.println("Registered user: " + newUser.toString());
            
            userRepository.save(newUser);
            responseBody.put("status", 200);
            responseBody.put("message", "Account Successfully Created!");        	
        	
        } else {
        	responseBody.put("status", 400);
            responseBody.put("message", "Please enter a valid password. -*It must contain a capital,lowerCase letter and a digit number *-");   
        }
        
        return responseBody;
    }
	
	/*
	 * Checks if the password contains a upper, lower case letter & a numeric value
	 */
	private static boolean checkString(String str) {
	    char ch;
	    boolean capitalFlag = false;
	    boolean lowerCaseFlag = false;
	    boolean numberFlag = false;
	    for(int i=0;i < str.length();i++) {
	        ch = str.charAt(i);
	        if( Character.isDigit(ch)) {
	            numberFlag = true;
	        }
	        else if (Character.isUpperCase(ch)) {
	            capitalFlag = true;
	        } else if (Character.isLowerCase(ch)) {
	            lowerCaseFlag = true;
	        }
	        if(numberFlag && capitalFlag && lowerCaseFlag)
	            return true;
	    }
	    return false;
	}
	
	
	/*
	 * Log in call 
	 */
    @PostMapping("/login")
    public JSONObject loginUser(@Valid @RequestBody User user) {
        List<User> users = userRepository.findAll();
        JSONObject responseBody = new JSONObject();

        for (User other : users) {
            if (other.equals(user)) {
                user.setLoggedIn(true);
                responseBody.put("status", 200);
                responseBody.put("message", "Login Successful");
                return responseBody;
            }
        }
        responseBody.put("status", 400);
        responseBody.put("message", "Login Failed");
        return responseBody;
    }

    /* 
     * Log out call 
     */
    @PostMapping("/logout")
    public JSONObject logUserOut(@RequestBody User user) {
        JSONObject responseBody = new JSONObject();

        List<User> users = userRepository.findAll();

        for (User other : users) {
            if (other.username.equals(user.username)) {
                user.setLoggedIn(false);
                responseBody.put("status", 200);
                responseBody.put("message", "User Successfully logged out");
                return responseBody;
            }
        }
        responseBody.put("status", 400);
        responseBody.put("message", "Failure to Logout");
        return responseBody;
    }

	/*
	 * to delete user detail from the database
	 */
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
    public JSONObject deleteUsers() {
        JSONObject responseBody = new JSONObject();
    	userRepository.deleteAll();

        responseBody.put("status", 200);
        responseBody.put("message", "Successfully deleted all users");
        return responseBody;
    }

	
}
