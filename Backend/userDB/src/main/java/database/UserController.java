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


@RestController 
public class UserController {
	
	@Autowired
	UserDB db;

//	to get User details by ID from the database
	@GetMapping("/user/{id}")  //("/user/{id}") is the endpoint 
	User getUserName(@PathVariable Integer id) {
//		return db.findById(id);	
		return db.findById(id).get();	
	}
	
//	to get all the Users details from the database
	@RequestMapping("/user")
	List<User> getAllUsers() {
		return db.findAll();
	}
	
//	to create user to the database
	@PostMapping("/user")
	User createUser(@RequestBody User u) {
		db.save(u);
		return u;
	}

////	to delete user detail from the database
//	@DeleteMapping("/user/{id}")
//	User deleteUser(@PathVariable Integer id) {
//		db.delete(id);
//		return "deleted user: " + id;
//	}
	
	
	
}
