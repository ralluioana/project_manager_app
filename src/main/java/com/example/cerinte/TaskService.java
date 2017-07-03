package com.example.cerinte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<TaskModel> getAllTasks()
    {
        List<TaskModel> taskModels =new ArrayList<>();
        //user.findAll().forEach(topics::add);
        for(TaskModel taskModel : taskRepository.findAll())
        {
            taskModels.add(taskModel);
        }
        return taskModels;
    }
    public TaskModel getTask(String id)
    {
//      return user.findOne(id);
        List<TaskModel> taskModels = new ArrayList<>();
        taskRepository.findAll().forEach(taskModels::add);
        for(TaskModel model : taskModels)
        {
            if(model.getID().equals(id)){
                return model;
            }
        }
        return taskModels.stream().filter(t-> t.getID().equals(id)).findFirst().get();
    }

//    public ConcediiModel getTaskByEchipa(String echipa)
//    {
////      return user.findOne(id);
//        List<ConcediiModel> taskModels = new ArrayList<>();
//        taskRepository.findAllByEchipa(echipa).forEach(taskModels::add);
//        for(ConcediiModel model : taskModels)
//        {
//            if(model.getEchipa().equals(echipa)){
//                return model;
//            }
//        }
//        return taskModels.stream().filter(t-> t.getEchipa().equals(echipa)).findFirst().get();
//    }

    public void addTask(TaskModel taskModel){
          taskRepository.save(taskModel);
    }
    public void addAllTasks(Collection<TaskModel> taskModels){
        for(TaskModel taskModel:taskModels)
        taskRepository.save(taskModel);
    }

    public List<TaskModel> findAllByEchipa(String echipa){
       return taskRepository.findAllByEchipa(echipa);
    }

    public void updateTask(TaskModel taskModel, String id) {
        taskRepository.save(taskModel);
    }

    public void deleteTask(String id) {
        taskRepository.delete(id);
    }
}
