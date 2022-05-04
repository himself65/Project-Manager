package com.splask;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestingSystemTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }


    @Test
    public void registerUserTest() {
        // Send request and receive response
        Response response = RestAssured.given().
                body(). //parameters that we are testing, eg. name, id,
                when().
                post(); //endpoint

        // Check status code
        int statusCode = response.statusCode();
        // The status code varies depending on the call, if its a PUT, DELETE, GET, POST, etc
        assertEquals(200, statusCode);






    }





}
