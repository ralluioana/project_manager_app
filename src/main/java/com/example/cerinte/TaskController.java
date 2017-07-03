package com.example.cerinte;
/**
 * Created by Crisan on 15.03.2017.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/taskservice")
public class TaskController {
    //dependency for the business service

    @Autowired
    private TaskService taskService;


    @RequestMapping("/tasks")
    public List<TaskModel> getAllTasks()
    {
        return taskService.getAllTasks();
    }
    //{id} variable input
    @RequestMapping("/task/{id}")
    public TaskModel getTask(@PathVariable String id)
    {
        return taskService.getTask(id);
    }

    //post request
    @RequestMapping(method = RequestMethod.POST, value = "/tasks")
    public void addTask(@RequestBody TaskModel taskModel){
        taskService.addTask(taskModel);
    }
    //post request
    @RequestMapping(method = RequestMethod.POST, value = "/task")
    public void addTask(@RequestBody Collection<TaskModel> taskModelx){
        taskService.addAllTasks(taskModelx);
    }
    //put request
    @RequestMapping(method = RequestMethod.PUT, value = "/task/{id}")
    public void updateTopic(@RequestBody TaskModel users, @PathVariable String id ){
        taskService.updateTask(users,id);
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/task/{id}")
    public void deleteTask(@PathVariable String id ) {
        taskService.deleteTask(id);
    }

}
