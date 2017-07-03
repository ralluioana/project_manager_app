package com.example;


import com.example.functii.FunctiiModel;
import com.example.sprint.SprintModel;
import com.google.gson.Gson;
import com.vaadin.shared.ui.ui.UIState;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crisanr on 6/22/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class AddSprintsRestTest {

    final String BASE_URL = "http://localhost:8083/";

        @Test
        public void addSprintsTest() {

            Gson gson = new Gson();

            String now = "2017-06-09 10:30";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime formatDateTime = LocalDateTime.parse(now, formatter);

     //send post requests to the server
            RestTemplate rest = new RestTemplate();
            Collection<SprintModel> sprintModels=new ArrayList<>();
//            sprintModels.add(new SprintModel("1",formatDateTime,LocalDateTime.parse("2017-06-25 10:30", formatter),10));
//            sprintModels.add(new SprintModel("2",LocalDateTime.parse("2017-06-26 10:30", formatter),LocalDateTime.parse("2017-07-10 10:30", formatter),9));
//            sprintModels.add(new SprintModel("3",LocalDateTime.parse("2017-07-11 10:30", formatter),LocalDateTime.parse("2017-07-25 10:30", formatter),10));

            sprintModels.add(new SprintModel("1",10));
            sprintModels.add(new SprintModel("2",9));
            sprintModels.add(new SprintModel("3",10));


            for(SprintModel sprintModel:sprintModels) {
                ResponseEntity<String> response1 =
                        rest.postForEntity(BASE_URL + "/sprintservice/sprint", sprintModel, String.class);
                Assert.assertTrue(HttpStatus.OK.equals(response1.getStatusCode()));
                //verify the created data- get call
                ResponseEntity<String> response2 =
                        rest.getForEntity(BASE_URL + "/sprintservice/sprint/" + sprintModel.getNumarSprint(), String.class);
                SprintModel sprintModelCreated1 = gson.fromJson(response2.getBody(), SprintModel.class);
                Assert.assertTrue(HttpStatus.OK.equals(response2.getStatusCode()));
                assertThat(response2.getBody().contains(sprintModel.getNumarSprint()));
                assertThat(sprintModelCreated1.equals(sprintModel));

            }

            for(SprintModel sprintModel:sprintModels) {
                ResponseEntity<String> response7 =
                        rest.getForEntity(BASE_URL + "/sprintservice/sprint" , String.class);
                assertThat(response7.getBody().contains(sprintModel.getNumarSprint()));
            }
        }

    }
