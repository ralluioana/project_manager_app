package com.example.sprint;
/**
 * Created by Crisan on 15.03.2017.
 */

import com.example.functii.FunctiiModel;
import com.example.functii.FunctiiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sprintservice")
public class SprintController {
    //dependency for the business service

    @Autowired
    private SprintService sprintService;


    @RequestMapping("/sprint")
    public List<SprintModel> getAllSprints()
    {
        return sprintService.getAllSprints();
    }
    //{id} variable input
    @RequestMapping("/sprint/{id}")
    public SprintModel getSprint(@PathVariable String id)
    {
        return sprintService.getSprint(id);
    }

    //post request
    @RequestMapping(method = RequestMethod.POST, value = "/sprint")
    public void addSprint(@RequestBody SprintModel sprintModel){
        sprintService.addSprint(sprintModel);
    }
    //put request
    @RequestMapping(method = RequestMethod.PUT, value = "/sprint/{id}")
    public void updateSprint( @RequestBody SprintModel sprintModel ){
        sprintService.updateSprint(sprintModel);
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/sprint/{id}")
    public void deleteFunctie(@PathVariable String id ) {
        sprintService.deleteSprint(id);
    }

}
