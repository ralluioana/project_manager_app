package com.example.concedii;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConcediuService {
    @Autowired
    private ConcediuRepository concediuRepository;

    public List<ConcediiModel> getAllConcedii()
    {
        List<ConcediiModel> concediiModels =new ArrayList<>();
        //user.findAll().forEach(topics::add);
        for(ConcediiModel concediiModel : concediuRepository.findAll())
        {
            concediiModels.add(concediiModel);
        }
        return concediiModels;
    }
    public ConcediiModel getConcediu(int id)
    {
//      return user.findOne(id);
        List<ConcediiModel> concediiModels = new ArrayList<>();
        concediuRepository.findAll().forEach(concediiModels::add);
        for(ConcediiModel model : concediiModels)
        {
            if(model.getNumarConcediu()==id){
                return model;
            }
        }
        return concediiModels.stream().filter(t-> t.getNumarConcediu()==id).findFirst().get();
    }

//    public ConcediiModel getTaskByEchipa(String echipa)
//    {
////      return user.findOne(id);
//        List<ConcediiModel> taskModels = new ArrayList<>();
//        concediuRepository.findAllByEchipa(echipa).forEach(taskModels::add);
//        for(ConcediiModel model : taskModels)
//        {
//            if(model.getEchipa().equals(echipa)){
//                return model;
//            }
//        }
//        return taskModels.stream().filter(t-> t.getEchipa().equals(echipa)).findFirst().get();
//    }

    public void addConcediu(ConcediiModel concediiModel){
          concediuRepository.save(concediiModel);
    }

    public void updateConcediu(ConcediiModel concediiModel, String id) {
        concediuRepository.save(concediiModel);
    }

    public void deleteConcediu(int id) {
        concediuRepository.delete(id);
    }
}
