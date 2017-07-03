package com.example;


import com.example.echipe.EchipaModel;
import com.example.user.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crisanr on 6/22/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class AddEchipeRestTest {

    final String BASE_URL = "http://localhost:8083/";

        @Test
        public void addEchipeTest() {

            Gson gson = new Gson();
     //send post requests to the server
            RestTemplate rest = new RestTemplate();
            EchipaModel echipaModel1= new EchipaModel("Echipa 1","Catanase Mirela","Catanase Mirela");
            EchipaModel echipaModel2= new EchipaModel("Echipa 2","Vestin Marcel","Vestin Marcel");
            EchipaModel echipaModel3= new EchipaModel("Echipa 3","Marculescu Dorel"," Marculescu Dorel");
                ResponseEntity<String> response1 =
                        rest.postForEntity(BASE_URL + "/echipaservice/echipa", echipaModel1, String.class);
                Assert.assertTrue(HttpStatus.OK.equals(response1.getStatusCode()));
                //verify the created data- get call
                ResponseEntity<String> response2 =
                        rest.getForEntity(BASE_URL + "/echipaservice/echipa/" + echipaModel1.getNumeEchipa(), String.class);
                EchipaModel echipaModelCreated1 = gson.fromJson(response2.getBody(), EchipaModel.class);
                Assert.assertTrue(HttpStatus.OK.equals(response2.getStatusCode()));
                assertThat(response2.getBody().contains(echipaModel1.getNumeEchipa()));
                assertThat(echipaModelCreated1.equals(echipaModel1));

            ResponseEntity<String> response3 =
                    rest.postForEntity(BASE_URL + "/echipaservice/echipa", echipaModel2, String.class);
            Assert.assertTrue(HttpStatus.OK.equals(response3.getStatusCode()));
            //verify the created data- get call
            ResponseEntity<String> response4 =
                    rest.getForEntity(BASE_URL + "/echipaservice/echipa/" + echipaModel2.getNumeEchipa(), String.class);
            EchipaModel echipaModelCreated2 = gson.fromJson(response4.getBody(), EchipaModel.class);
            Assert.assertTrue(HttpStatus.OK.equals(response4.getStatusCode()));
            assertThat(response4.getBody().contains(echipaModel2.getNumeEchipa()));
            assertThat(echipaModelCreated2.equals(echipaModel2));

            ResponseEntity<String> response5 =
                    rest.postForEntity(BASE_URL + "/echipaservice/echipa", echipaModel3, String.class);
            Assert.assertTrue(HttpStatus.OK.equals(response5.getStatusCode()));
            //verify the created data- get call
            ResponseEntity<String> response6 =
                    rest.getForEntity(BASE_URL + "/echipaservice/echipa/" + echipaModel3.getNumeEchipa(), String.class);
            EchipaModel echipaModelCreated3 = gson.fromJson(response6.getBody(), EchipaModel.class);
            Assert.assertTrue(HttpStatus.OK.equals(response6.getStatusCode()));
            assertThat(response6.getBody().contains(echipaModel3.getNumeEchipa()));
            assertThat(echipaModelCreated3.equals(echipaModel3));

            ResponseEntity<String> response7 =
                    rest.getForEntity(BASE_URL + "/echipaservice/echipe" , String.class);
            assertThat(response7.getBody().contains(echipaModel1.getNumeEchipa())&&response7.getBody().contains(echipaModel2.getNumeEchipa())&&response7.getBody().contains(echipaModel3.getNumeEchipa()));
            }

    @Test
    public void addAngajatiInEchipeTest() {

        Gson gson = new Gson();
        //send post requests to the server
        RestTemplate rest = new RestTemplate();

        ResponseEntity<String> responseUsers =
                rest.getForEntity(BASE_URL + "/userservice/users" , String.class);
        List<UserModel> users = gson.fromJson(responseUsers.getBody(), new TypeToken<List<UserModel>>(){}.getType());

        for(UserModel userModel:users) {
            if(!userModel.getEchipa().equals("fara echipa")) {
                ResponseEntity<String> responseEchipa =
                        rest.getForEntity(BASE_URL + "/echipaservice/echipa/" + userModel.getEchipa(), String.class);
                EchipaModel echipaModel = gson.fromJson(responseEchipa.getBody(), EchipaModel.class);
                Assert.assertTrue(HttpStatus.OK.equals(responseEchipa.getStatusCode()));
                if (echipaModel.getCoechipieri() .equals( "")||echipaModel.getCoechipieri().equals(" ")) {
                    echipaModel.setCoechipieri(userModel.getNume() + " " + userModel.getPrenume());
                } else {
                    echipaModel.setCoechipieri(echipaModel.getCoechipieri() + ", " + userModel.getNume() + " " + userModel.getPrenume());
                }
                ResponseEntity<String> response5 =
                        rest.postForEntity(BASE_URL + "/echipaservice/echipa", echipaModel, String.class);
                Assert.assertTrue(HttpStatus.OK.equals(response5.getStatusCode()));
            }
        }
    }
}
