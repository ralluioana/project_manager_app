package com.example.functii;

import com.example.config.Functii;
import com.example.echipe.EchipaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FunctiiService {
    @Autowired
    private FunctiiRepository functiiRepository;

    public List<FunctiiModel> getAllFunctii()
    {
        List<FunctiiModel> echipaModels =new ArrayList<>();
        //user.findAll().forEach(topics::add);
        for(FunctiiModel echipaModel : functiiRepository.findAll())
        {
            echipaModels.add(echipaModel);
        }
        return echipaModels;
    }
    public FunctiiModel getFunctie(String numeEchipa)
    {
//      return user.findOne(id);
        List<FunctiiModel> echipaModels = new ArrayList<>();
        functiiRepository.findAll().forEach(echipaModels::add);
        for(FunctiiModel model :echipaModels)
        {
            if(model.getNumeFunctie().equals(numeEchipa)){
                return model;
            }
        }
        return echipaModels.stream().filter(t-> t.getNumeFunctie().equals(numeEchipa)).findFirst().get();
    }


    public void addFunctie(FunctiiModel functiiModel){
          functiiRepository.save(functiiModel);
    }

    public void updateFunctie(FunctiiModel functiiModel, String id) {
        functiiRepository.save(functiiModel);
    }

    public void deleteFunctie(String nume) {
        functiiRepository.delete(nume);
    }

}
