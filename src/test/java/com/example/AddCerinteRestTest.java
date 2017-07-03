package com.example;


import com.example.cerinte.TaskModel;
import com.example.echipe.EchipaModel;
import com.example.user.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Id;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crisanr on 6/22/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class AddCerinteRestTest {

    final String BASE_URL = "http://localhost:8083/";

        @Test
        public void addCerinteTest() {

            Gson gson = new Gson();
     //send post requests to the server
            RestTemplate rest = new RestTemplate();

                ResponseEntity<String> responseUsers =
                        rest.getForEntity(BASE_URL + "/userservice/users" , String.class);
                List<UserModel> users = gson.fromJson(responseUsers.getBody(), new TypeToken<List<UserModel>>(){}.getType());
            int i=1;
                for(UserModel userModel:users)
                {
                    int timp=1;
                    if(i<10)
                    {
                        timp=timp+i;
                    }
                    else{
                        if(i>10)
                        {
                            timp=4;
                        }
                        else{
                            timp=timp+i-10;
                        }
                    }
                    TaskModel taskModel=new TaskModel(Integer.toString(i),userModel.getNume()+" "+userModel.getPrenume(),userModel.getEchipa(),userModel.getFunctie(),
                            "Descriere cerinta "+i,Integer.toString(timp),new Random(700).nextDouble()*100,"1");

                    ResponseEntity<String> responsePostTask =
                            rest.postForEntity(BASE_URL + "/taskservice/tasks",taskModel , String.class);
                    Assert.assertTrue(HttpStatus.OK.equals(responsePostTask.getStatusCode()));
                    //verify the created data- get call
                    ResponseEntity<String> response2 =
                            rest.getForEntity(BASE_URL + "/taskservice/task/" + taskModel.getID(), String.class);
                    TaskModel taskModelCreated = gson.fromJson(response2.getBody(), TaskModel.class);
                    Assert.assertTrue(HttpStatus.OK.equals(response2.getStatusCode()));
                    assertThat(response2.getBody().contains(taskModel.getID()));
                    assertThat(taskModelCreated.equals(taskModel));
                    i++;
                }

            for(UserModel userModel:users) {
                int timp = 0;
                if (i < 10) {
                    timp = timp + i - 1;
                } else {
                    if (i > 10) {
                        timp = 4;
                    } else {
                        timp = timp + i - 10;
                    }
                }
            }

            for(UserModel userModel:users)
            {
                int timp=1;
                if(i<10)
                {
                    timp=timp+i;
                }
                else{
                    if(i>10)
                    {
                        timp=4;
                    }
                    else{
                        timp=timp+i-10;
                    }
                }
                TaskModel taskModel=new TaskModel(Integer.toString(i),userModel.getNume()+" "+userModel.getPrenume(),userModel.getEchipa(),userModel.getFunctie(),
                        "Descriere cerinte "+i,Integer.toString(timp),new Random(70).nextDouble()*100,"2");

                ResponseEntity<String> responsePostTask =
                        rest.postForEntity(BASE_URL + "/taskservice/tasks",taskModel , String.class);
                Assert.assertTrue(HttpStatus.OK.equals(responsePostTask.getStatusCode()));
                //verify the created data- get call
                ResponseEntity<String> response2 =
                        rest.getForEntity(BASE_URL + "/taskservice/task/" + taskModel.getID(), String.class);
                TaskModel taskModelCreated = gson.fromJson(response2.getBody(), TaskModel.class);
                Assert.assertTrue(HttpStatus.OK.equals(response2.getStatusCode()));
                assertThat(response2.getBody().contains(taskModel.getID()));
                assertThat(taskModelCreated.equals(taskModel));
                i++;
            
        }
            for(UserModel userModel:users)
            {
                int timp=1;
                if(i<10)
                {
                    timp=timp+i;
                }
                else{
                    if(i>10)
                    {
                        timp=4;
                    }
                    else{
                        timp=timp+i-10;
                    }
                }
                TaskModel taskModel=new TaskModel(Integer.toString(i),userModel.getNume()+" "+userModel.getPrenume(),userModel.getEchipa(),userModel.getFunctie(),
                        "Descriere cerinta "+i,Integer.toString(timp), ((double) i),"2");

                ResponseEntity<String> responsePostTask =
                        rest.postForEntity(BASE_URL + "/taskservice/tasks",taskModel , String.class);
                Assert.assertTrue(HttpStatus.OK.equals(responsePostTask.getStatusCode()));
                //verify the created data- get call
                ResponseEntity<String> response2 =
                        rest.getForEntity(BASE_URL + "/taskservice/task/" + taskModel.getID(), String.class);
                TaskModel taskModelCreated = gson.fromJson(response2.getBody(), TaskModel.class);
                Assert.assertTrue(HttpStatus.OK.equals(response2.getStatusCode()));
                assertThat(response2.getBody().contains(taskModel.getID()));
                assertThat(taskModelCreated.equals(taskModel));
                i++;

            }
        }
    
    }
