package database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping("/user/{id}")
	User getUserName(@PathVariable Integer id) {
		return db.findById(id);	
	}
	
	@RequestMapping("/user")
	List<User> hello() {
		return db.findAll();
	}
	
	@PostMapping("/user")
	User createPerson(@RequestBody User u) {
		db.save(u);
		return u;
	}
	
	
	
}
