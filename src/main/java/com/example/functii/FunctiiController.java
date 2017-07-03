package com.example.functii;
/**
 * Created by Crisan on 15.03.2017.
 */

import com.example.echipe.EchipaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/functiiservice")
public class FunctiiController {
    //dependency for the business service

    @Autowired
    private FunctiiService functiiService;


    @RequestMapping("/functii")
    public List<FunctiiModel> getallFunctii()
    {
        return functiiService.getAllFunctii();
    }
    //{id} variable input
    @RequestMapping("/functie/{id}")
    public FunctiiModel getFunctie(@PathVariable String id)
    {
        return functiiService.getFunctie(id);
    }

    //post request
    @RequestMapping(method = RequestMethod.POST, value = "/functie")
    public void addFunctie(@RequestBody FunctiiModel functiiModel){
        functiiService.addFunctie(functiiModel);
    }
    //put request
    @RequestMapping(method = RequestMethod.PUT, value = "/functie/{id}")
    public void updateFunctie( @RequestBody FunctiiModel functiiMode,@PathVariable String numeFunctie ){
        functiiService.updateFunctie(functiiMode,numeFunctie);
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/functie/{id}")
    public void deleteFunctie(@PathVariable String id ) {
        functiiService.deleteFunctie(id);
    }

}
