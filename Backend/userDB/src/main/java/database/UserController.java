package database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import database.User;



@RestController 
public class UserController {
	
	@Autowired
	UserDB userRepository;

//	to get User details by ID from the database
//	@GetMapping("/user/{id}")  //("/user/{id}") is the endpoint 
//	User getUserName(@PathVariable Long id) {
//		return userRepository.findById(id).get();	
//	}
	
	@GetMapping("/user/{id}")  //("/user/{id}") is the endpoint 
	User getUserName(@PathVariable Integer id) {
		return userRepository.findById(id).get();	
	}
	
//	to get all the Users details from the database
	@RequestMapping("/users")
	List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
/*
 * Registers user to the database and checks if there is an
 * existing user registered with the same parameters 
 */
	@PostMapping("/user/register")
	public Status registerUser(@RequestBody User newUser) {
		
		List<User> users = userRepository.findAll();

        System.out.println("New user: " + newUser.toString());

        for (User user : users) {
            System.out.println("Registered user: " + newUser.toString());

            if (user.equals(newUser)) {
                System.out.println("User Already exists!");
                return Status.USER_ALREADY_EXISTS;
            }
        }

        userRepository.save(newUser);
        return Status.SUCCESS;
    }
	
	/*
	 * Log in call 
	 */
//    @PostMapping("/users/login")
//    public Status loginUser(@RequestBody User user) {
//        List<User> users = userRepository.findAll();
//
//        for (User other : users) {
//            if (other.equals(user)) {
//                user.setLoggedIn(true);
//                userRepository.save(user);
//                return Status.SUCCESS;
//            }
//        }
//
//        return Status.FAILURE;
//    }
//
//    /* 
//     * Log out call 
//     */
//    @PostMapping("/users/logout")
//    public Status logUserOut(@RequestBody User user) {
//        List<User> users = userRepository.findAll();
//
//        for (User other : users) {
//            if (other.equals(user)) {
//                user.setLoggedIn(false);
//                userRepository.save(user);
//                return Status.SUCCESS;
//            }
//        }
//
//        return Status.FAILURE;
//    }
//
//	to delete user detail from the database
//	@DeleteMapping("/user/{id}")
//	String deleteUser(@PathVariable Long id) {
//		userRepository.deleteById(id);
//		return "deleted user: " + id;
//	}
	
	@DeleteMapping("/user/{id}")
	String deleteUser(@PathVariable Integer id) {
		userRepository.deleteById(id);
		return "deleted user: " + id;
	}

//	Delete ALL users
    @DeleteMapping("/users/all")
    public Status deleteUsers() {
    	userRepository.deleteAll();
        return Status.SUCCESS;
    }

	
}
