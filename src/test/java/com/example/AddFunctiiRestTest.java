package com.example;


import com.example.config.Functii;
import com.example.echipe.EchipaModel;
import com.example.functii.FunctiiModel;
import com.example.user.UserModel;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crisanr on 6/22/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class AddFunctiiRestTest {

    final String BASE_URL = "http://localhost:8083/";

        @Test
        public void addFunctiiTest() {

            Gson gson = new Gson();
     //send post requests to the server
            RestTemplate rest = new RestTemplate();
            Collection<FunctiiModel> functiiModels=new ArrayList<>();
            functiiModels.add(new FunctiiModel("DEV"));
            functiiModels.add(new FunctiiModel("QA"));
            functiiModels.add(new FunctiiModel("MANAGER"));
            functiiModels.add(new FunctiiModel("SCRUM MASTER"));
            functiiModels.add(new FunctiiModel("DOCUMENTATION"));

            for(FunctiiModel functiiModel:functiiModels) {
                ResponseEntity<String> response1 =
                        rest.postForEntity(BASE_URL + "/functiiservice/functie", functiiModel, String.class);
                Assert.assertTrue(HttpStatus.OK.equals(response1.getStatusCode()));
                //verify the created data- get call
                ResponseEntity<String> response2 =
                        rest.getForEntity(BASE_URL + "/functiiservice/functie/" + functiiModel.getNumeFunctie(), String.class);
                FunctiiModel functieModelCreated1 = gson.fromJson(response2.getBody(), FunctiiModel.class);
                Assert.assertTrue(HttpStatus.OK.equals(response2.getStatusCode()));
                assertThat(response2.getBody().contains(functiiModel.getNumeFunctie()));
                assertThat(functieModelCreated1.equals(functiiModel));

            }

            for(FunctiiModel functiiModel:functiiModels) {

                ResponseEntity<String> response7 =
                        rest.getForEntity(BASE_URL + "/functiiservice/functii" , String.class);
                assertThat(response7.getBody().contains(functiiModel.getNumeFunctie()));
            }
        }

    }
