package com.example.echipe;

import com.example.user.UserModel;
import com.example.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EchipaService  {
    @Autowired
    private EchipaRepository echipaRepository;

//   private List<Topic> topics=new ArrayList<>( Arrays.asList(new Topic("spring","sping framework","descption"),
//                new Topic("spring2","spi1ng framework2","descption2")));

    public List<EchipaModel> getAllEchipe()
    {
        List<EchipaModel> echipaModels =new ArrayList<>();
        //user.findAll().forEach(topics::add);
        for(EchipaModel echipaModel : echipaRepository.findAll())
        {
            echipaModels.add(echipaModel);
        }
        return echipaModels;
    }
    public EchipaModel getEchipa(String numeEchipa)
    {
//      return user.findOne(id);
        List<EchipaModel> echipaModels = new ArrayList<>();
        echipaRepository.findAll().forEach(echipaModels::add);
        for(EchipaModel model :echipaModels)
        {
            if(model.getNumeEchipa().equals(numeEchipa)){
                return model;
            }
        }
        return echipaModels.stream().filter(t-> t.getNumeEchipa().equals(numeEchipa)).findFirst().get();
    }


    public void addEchipa(EchipaModel echipaModel){
          echipaRepository.save(echipaModel);
    }

    public void updateEchipa(EchipaModel echipaModel, String id) {
        echipaRepository.save(echipaModel);
    }

    public void deleteEchipa(String nume) {
        echipaRepository.delete(nume);
    }

}
