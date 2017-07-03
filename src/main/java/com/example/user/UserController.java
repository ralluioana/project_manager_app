package com.example.user;
/**
 * Created by Crisan on 15.03.2017.
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userservice")
public class UserController {
    //dependency for the business service

    @Autowired
    private UserService userService;


    @RequestMapping("/users")
    public List<UserModel> getAllTopics()
    {
        return userService.getAllUsers();
    }
    //{id} variable input
    @RequestMapping("/users/{id}")
    public UserModel getUser(@PathVariable String id)
    {
        return userService.getUser(id);
    }


    //post request
    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public ResponseEntity<String> addTopic(@RequestBody UserModel userModel){
        userService.addUser(userModel);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    //put request
    @RequestMapping(method = RequestMethod.PUT, value = "/user/{id}")
    public ResponseEntity<String> updateTopic(@RequestBody UserModel users, @PathVariable String id ){
        userService.updateUser(users,id);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable String id ) {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
