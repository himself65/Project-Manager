package com.splask;

import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class SystemTests {

    @Before
    public void setUp() {
        baseURI = "http://coms-309-007.class.las.iastate.edu:8080";
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
                .body(request.toJSONString()) //parameters that we are testing, eg. name, id,
                .when()
                .put("/login") //endpoint
                .then()
                .statusCode(200).log().all();
    }


    @Test
    public void logoutUser(){

        JSONObject request = new JSONObject();

        request.put("username", "andrew");
//        request.put("userPassword", "12345");

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString()) //parameters that we are testing, eg. name, id,
                .when()
                .put("/logout") //endpoint
                .then()
                .statusCode(200).log().all();
    }


    @Test
    public void getUserProjects(){

        JSONObject request = new JSONObject();

        request.put("username", "andrew");
        request.put("userPassword", "12345");

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString()) //parameters that we are testing, eg. name, id,
                .when()
                .put("/user/{user_id}/projects") //endpoint
                .then()
                .statusCode(200).log().all();

    }









}
