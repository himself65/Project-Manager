package coms309.people;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Vivek Bengre
 */

@RestController
public class PeopleController {

    // Note that there is only ONE instance of PeopleController in 
    // Springboot system.
    HashMap<Integer, Person> peopleList = new  HashMap<>();
    int count = 0;

    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the people in the list and returns it in JSON format
    // This controller takes no input. 
    // Springboot automatically converts the list to JSON format 
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/people")
    public String getAllPersons() {
    	String returnString = "List of Users:\n";
    	for(int i = 0; i < peopleList.size(); i++) {
    		if(peopleList.get(i) != null) {
        		returnString += (i + ": " + peopleList.get(i).getUsername() + "\n");
    		}
    	}
        return returnString;
    }

    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a person object and 
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/people")
    public @ResponseBody String createPerson(@RequestBody Person person) {
        System.out.println(person);
        peopleList.put(count, person);
        count++;
        return "Registered new user: "+ person.getFirstName() + " " + person.getLastName();
    }

    // THIS IS THE READ OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We extract the person from the HashMap.
    // springboot automatically converts Person to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/people/{id}")
    public @ResponseBody Person getPerson(@PathVariable Integer id) {
        Person p = peopleList.get(id);
        if(p != null) {
        	return p;
        }
        return null;
    }

    // THIS IS THE UPDATE OPERATION
    // We extract the person from the HashMap and modify it.
    // Springboot automatically converts the Person to JSON format
    // Springboot gets the PATHVARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/people/{id}")
    public String updatePerson(@PathVariable Integer id, @RequestBody Person p) {
    	String returnStr = "Successfully Updated User!\n";
    	if(peopleList.get(id) != null) {
        	returnStr += "Old data:\nUsername: " + peopleList.get(id).getUsername() + "\nFirst Name: " + peopleList.get(id).getFirstName()
        			+ "\nLast Name: " + peopleList.get(id).getLastName() + "\nPassword: " + peopleList.get(id).getPassword();
        	returnStr +="\n\nNew data:\nUsername: " + p.getUsername() + "\nFirst Name: " + p.getFirstName() + "\nLast Name: " + p.getLastName()
        				+ "\nPassword: " + p.getPassword();
            peopleList.replace(id, p);
    	}
    	else {
    		returnStr = "Invalid User!\n";
    	}
        return returnStr;
    }

    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method
    
    @DeleteMapping("/people/{id}")
    public String deletePerson(@PathVariable Integer id) {
    	if(peopleList.get(id) != null) {
        	String returnStr = "Removed user: " + peopleList.get(id).getUsername();
            peopleList.remove(id);
            return returnStr;
    	}
    	return "Failed to delete user: Doesn't Exist!\n";
    }
}

