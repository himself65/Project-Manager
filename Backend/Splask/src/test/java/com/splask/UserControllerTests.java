package com.splask;

import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
public class UserControllerTests {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
//        baseURI = "http://coms-309-007.class.las.iastate.edu:8080";
        port = port;
        baseURI = "http://localhost";
    }

    @Test
    public void getAllUsers() {

        given().get("/user").then().statusCode(200).log().all();
    }


    @Test
    public void registerNewUser(){

        JSONObject request = new JSONObject();

        request.put("username", "Andrew");
        request.put("userPassword", "12345");
        request.put("full_name", "Andrew Chiang");

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString()) //parameters that we are testing, eg. name, id,
                .when()
                    .post("/register") //endpoint
                .then()
                    // The status code varies depending on the call, if it's a PUT, DELETE, GET, POST, etc
                    .statusCode(200).log().all();
    }


    @Test
    public void loginUser(){

        JSONObject request = new JSONObject();

        request.put("username", "andrew");
        request.put("userPassword", "andrew1");
//        TODO Should fail the test, however it passes but it does not logs in the user and sends the fail mesaage to login
//        request.put("username", "Andrew");
//        request.put("userPassword", "andrew111");

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/login")
                .then()
                .statusCode(200).log().all();
    }


    @Test
    public void logoutUser(){

        JSONObject request = new JSONObject();

        request.put("username", "andrew");
//        request.put("userPassword", "12345");

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/logout")
                .then()
                .statusCode(200).log().all();
    }


    @Test
    public void getUserProjects(){

        int user_id = 8;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body("")
                .when()
                .get("/user/" + user_id + "/projects")
                .then()
                .statusCode(200).log().all();

    }


    @Test
    public void getUserTeams(){

        int user_id = 3;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body("") //parameters that we are testing, eg. name, id,
                .when()
                .get("/user/" + user_id + "/teams")
                .then()
                .statusCode(200).log().all();

    }


    @Test
    public void getUserTasks(){

        int user_id = 8;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body("")
                .when()
                .get("/user/" + user_id + "/tasks")
                .then()
                .statusCode(200).log().all();

    }


    @Test
    public void getImageById(){

        int user_id = 8;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body("")
                .when()
                .get("/user/" + user_id + "/image")
                .then()
                .statusCode(200).log().all();
    }


    @Test
    public void uploadImageToUser(){

        int user_id = 8;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body("")
                .when()
                .put("/user/" + user_id + "/image")
                .then()
                .statusCode(200).log().all();
    }





}
