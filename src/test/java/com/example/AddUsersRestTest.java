package com.example;


import com.example.user.UserModel;

import com.google.gson.Gson;
import org.hibernate.mapping.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.CollectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.jws.soap.SOAPBinding;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by crisanr on 6/22/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class AddUsersRestTest {

    final String BASE_URL = "http://localhost:8083/";
    @Test
    public void addUserFlowTest() {
        final String USER_NAME = "popAurel ";
        final String Nume = "Pop ";
        final String Prenume = "Aurel ";
        final String Echipa_Id = "echipa1";
        final int orepezi=8;

        UserModel userModel = new UserModel ();
        userModel.setUserName(USER_NAME);
        userModel.setPassword("pass");
        userModel.setEchipa(Echipa_Id);
        userModel.setNume(Nume);
        userModel.setPrenume(Prenume);
        userModel.setEmail(Nume+"."+Prenume+"@yahoo.com");
        userModel.setFunctie("DEV");
        userModel.setOrePeZi(orepezi);
        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        System.out.println(json);
//send post request to the server
        RestTemplate rest = new RestTemplate();

        ResponseEntity< String > response1 =
                rest.postForEntity(BASE_URL+"/userservice/users", userModel, String.class);
        Assert.assertTrue(HttpStatus.CREATED.equals(response1.getStatusCode()));
//verify the created data- get call
        ResponseEntity< String > response2 =
                rest.getForEntity(BASE_URL+"/userservice/users/"+USER_NAME, String.class);
        UserModel userCreated = gson.fromJson(response2.getBody(), UserModel.class);
        Assert.assertTrue(HttpStatus.OK.equals(response2.getStatusCode()));
        assertThat(response2.getBody().contains(USER_NAME) );
        assertThat(userCreated.equals(userModel));
 // perform delete and verify data
        userModel.setOrePeZi(4);
        rest.delete(BASE_URL+"/userservice/user/"+ USER_NAME);
        try {
            response2 = rest.getForEntity(BASE_URL+"/userservice/users/"+USER_NAME, String.class);
            Assert.assertTrue(HttpStatus.OK.equals(response2.getStatusCode()));
            assertThat(!response2.getBody().contains(USER_NAME) );
        }catch (Exception e)
        {
            assertThat(e.getMessage().contains("500 null")) ;
        }
    }


        @Test
        public void addUsersTest() {

            java.util.Collection<UserModel> users=new ArrayList<>();
            users.add(new UserModel("popAurel","pass","Pop","Aurel","pop.aurelyYahoo.com","Echipa 1",false,"DEV",8));
            users.add(new UserModel("catanaseMirela","pass","Catanase","Mirela","catanase.mirela@yahoo.com","Echipa 1",true,"MANAGER",8));
            users.add(new UserModel("andreicutCamelia","pass","Andreicut","Camelia","andreicut.camelia@yahoo.com","Echipa 1",false,"QA",6));
            users.add(new UserModel("bratescuIulian","pass","Bratescu","Iulian","bratescu.iulian@yahoo.com","Echipa 1",false,"QA",4));
            users.add(new UserModel("constantinescuMaria","pass","Constantinescu","Maria","Constantinescu.Maria@yahoo.com","Echipa 1",false,"DEV",6));
            users.add(new UserModel("popescuMarian","pass","Popescu","Marian","popescu.marian@yahoo.com","Echipa 2",false,"DEV",8));
            users.add(new UserModel("martinLavinia","pass","Martin","Lavinia","martin.lavinia@yahoo.com","Echipa 2",true,"SCRUM MASTER",8));
            users.add(new UserModel("popoviciMihai","pass","Popovici","Mihai","popovici.mihai@yahoo.com","Echipa 2",false,"QA",6));
            users.add(new UserModel("crisanRaluca","pass","Crisan","Raluca","crisan.raluca@yahoo.com","Echipa 2",false,"DEV",6));
            users.add(new UserModel("barbuLarisa","pass","Barbu","Larisa","barbu.larisa@yahoo.com","Echipa 2",false,"QA",8));
            users.add(new UserModel("vestinMarcel","pass","Vestin","Marcel","vestin.marcel@yahoo.com","Echipa 2",true,"MANAGER",8));
            users.add(new UserModel("hercutCristian","pass","Hercut","Cristian","hercut.cristian@yahoo.com","Echipa 3",false,"DEV",8));
            users.add(new UserModel("ZindeMihaela","pass","Zinde","Mihaela","zinde.mihaela@yahoo.com","Echipa 3",false,"QA",8));
            users.add(new UserModel("marculescuDorel","pass","Marculescu","Dorel","marculescu.dorel@yahoo.com","Echipa 3",true,"SCRUM MASTER",8));
            users.add(new UserModel("sorescuCosmin","pass","Sorescu","Cosmin","sorescu.cosmin@yahoo.com","Echipa 3",true,"QA",6));
            users.add(new UserModel("tatarCarla","pass","Tatar","Carla","tatar.carla@yahoo.com","Echipa 3",true,"DOCUMENTATION",8));

            Gson gson = new Gson();
     //send post requests to the server
            RestTemplate rest = new RestTemplate();

            for (UserModel userModel:users) {
                ResponseEntity<String> response1 =
                        rest.postForEntity(BASE_URL + "/userservice/users", userModel, String.class);
                Assert.assertTrue(HttpStatus.CREATED.equals(response1.getStatusCode()));
                //verify the created data- get call
                ResponseEntity<String> response2 =
                        rest.getForEntity(BASE_URL + "/userservice/users/" + userModel.getUserName(), String.class);
                UserModel userCreated = gson.fromJson(response2.getBody(), UserModel.class);
                Assert.assertTrue(HttpStatus.OK.equals(response2.getStatusCode()));
                assertThat(response2.getBody().contains(userModel.getUserName()));
                assertThat(userCreated.equals(userModel));
            }
        }

    }
