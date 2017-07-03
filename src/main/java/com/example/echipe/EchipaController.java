package com.example.echipe;
/**
 * Created by Crisan on 15.03.2017.
 */

import com.example.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/echipaservice")
public class EchipaController {
    //dependency for the business service

    @Autowired
    private EchipaService echipaService;


    @RequestMapping("/echipe")
    public List<EchipaModel> getallEchipe()
    {
        return echipaService.getAllEchipe();
    }
    //{id} variable input
    @RequestMapping("/echipa/{id}")
    public EchipaModel getEchipa(@PathVariable String id)
    {
        return echipaService.getEchipa(id);
    }

    //post request
    @RequestMapping(method = RequestMethod.POST, value = "/echipa")
    public void addEchipa(@RequestBody EchipaModel echipaModel){
        echipaService.addEchipa(echipaModel);
    }
    //put request
    @RequestMapping(method = RequestMethod.PUT, value = "/echipa/{id}")
    public void updateEchipa( @RequestBody EchipaModel echipaModel,@PathVariable String numeEchipa ){
        echipaService.updateEchipa(echipaModel,numeEchipa);
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/echipa/{id}")
    public void deleteEchipa(@PathVariable String id ) {
        echipaService.deleteEchipa(id);
    }

}
