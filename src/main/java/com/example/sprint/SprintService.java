package com.example.sprint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SprintService {
    @Autowired
    private SprintRepository sprintRepository;

    public List<SprintModel> getAllSprints()
    {
        List<SprintModel> sprintModels =new ArrayList<>();
        //user.findAll().forEach(topics::add);
        for(SprintModel sprintModel : sprintRepository.findAll())
        {
            sprintModels.add(sprintModel);
        }
        return sprintModels;
    }
    public SprintModel getSprint(String id)
    {
//      return sprintRepository.findOne(id);
        List<SprintModel> sprintModels = new ArrayList<>();
        sprintRepository.findAll().forEach(sprintModels::add);
        for(SprintModel model : sprintModels)
        {
            if(model.getNumarSprint().equals(id)){
                return model;
            }
        }
        return sprintModels.stream().filter(t-> t.getNumarSprint().equals(id)).findFirst().get();
    }

//    public ConcediiModel getTaskByEchipa(String echipa)
//    {
////      return user.findOne(id);
//        List<ConcediiModel> taskModels = new ArrayList<>();
//        sprintRepository.findAllByEchipa(echipa).forEach(taskModels::add);
//        for(ConcediiModel model : taskModels)
//        {
//            if(model.getEchipa().equals(echipa)){
//                return model;
//            }
//        }
//        return taskModels.stream().filter(t-> t.getEchipa().equals(echipa)).findFirst().get();
//    }

    public void addSprint(SprintModel sprintModel){
          sprintRepository.save(sprintModel);
    }

    public void updateSprint(SprintModel sprintModel) {
        sprintRepository.save(sprintModel);
    }

    public void deleteSprint(String id) {
        sprintRepository.delete(id);
    }
}
