package com.splask.Controller;

import com.splask.Models.Project;
import com.splask.Models.Role;
import com.splask.Models.User;
import com.splask.Repositories.UserDB;
import com.splask.Repositories.roleDB;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {

    @Autowired
    roleDB roleRepository;

    @Autowired
    UserDB userRepository;


    /**
     *
     * @param id
     * id is an integer that matches a role in the database
     * @return responseBody, if found returns the role object and a success status,
     *         if Failed to find sends a fail message.
     */

    @GetMapping("/role/{id}")
    JSONObject getRole(@PathVariable Integer id)
    {
        JSONObject responseBody = new JSONObject();
        if (!roleRepository.existsById(id))
        {
            responseBody.put("status", 400);
            responseBody.put("message", "Role does not exist");
        }

        responseBody.put("project",roleRepository.findById(id));
        responseBody.put("status", 200);
        responseBody.put("message", "Role successfully retrieved");

        return responseBody;
    }


    /**
     *
     * @return A JSONObject with a response code and a JSON array contain all
     *         roles in the database.
     */
    @RequestMapping("/role")
    JSONObject getAllRoles(){
        JSONObject responseBody = new JSONObject();
        JSONArray rolesArray = new JSONArray();

        rolesArray.addAll(roleRepository.findAll());

        responseBody.put("projects", rolesArray);
        responseBody.put("status", 200);
        responseBody.put("message", "Roles successfully retrieved");

        return responseBody;
    }





    /**
     * Adds existing users in database to a role by username
     * @param role_id
     * Integer id of the project
     * @param username
     * A JSON object containing a username of a User
     * @return JSON Response body of status codes, success if added
     *         fail code if user is already in project.
     */
    @PutMapping("/role/{role_id}/addUser")
    JSONObject enrollUserToRole( //Gets the user then assigns the user to the role
                                    @PathVariable Integer role_id,
                                    @RequestBody JSONObject username
    ) {
        JSONObject responseBody = new JSONObject();

        Role role = roleRepository.getById(role_id);
        User user = userRepository.findByUsername(username.getAsString("username")).get(0);


        if (role.getUsers().contains(user)) // check if user is curretly enrroled in the role
        {

            responseBody.put("status", 400);
            responseBody.put("message", user.getUsername() +" already in " + role.getRoleName());
            return responseBody;
        }

        role.enrollUserToRole(user); //sends the passed user to the enrollUserToRole method
        user.addRoleToUser(role);
        roleRepository.save(role);
        userRepository.save(user);

        responseBody.put("status", 200);
        responseBody.put("message", "User successfully assigned to " + role.getRoleName());
        return  responseBody; //saves the new user to assigned role
    }


    /**
     * retrieves all users in a role
     * @param role_id
     * Integer id of the role
     * @return responseBody containing a JSONArray of users from the role,
     *         and a success code
     */
    @GetMapping("/role/{role_id}/users")
    JSONObject usersInRole(@PathVariable Integer role_id)
    {
        Role role = roleRepository.getById(role_id);
        JSONArray users = new JSONArray();
        JSONObject responseBody = new JSONObject();

        users.addAll(role.getUsers());

        responseBody.put("users",users);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully retrieved all users from" + role.getRoleName());

        return responseBody;
    }



}
