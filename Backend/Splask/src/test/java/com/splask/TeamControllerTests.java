package com.splask;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
public class TeamControllerTests {

    @LocalServerPort
    int port;

    @Before
    public void setUp() throws Exception{
//        RestAssured.port = port;
//        RestAssured.baseURI = "http://localhost";
        baseURI = "http://coms-309-007.class.las.iastate.edu:8080";

    }


    @Test
    public void getAllTeams() {

        given().get("/team").then().statusCode(200).log().all();
    }


    @Test
    public void createNewTeam(){

        JSONObject request = new JSONObject();

//        request.put();

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post("/team") //endpoint
                .then()
                // The status code varies depending on the call, if it's a PUT, DELETE, GET, POST, etc
                .statusCode(200).log().all();
    }


    @Test
    public void usersInTeam(){

        int team_id = 3;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body("")
                .when()
                .get("/team/" + team_id + "/users")
                .then()
                .statusCode(200).log().all();

    }


    @Test
    public void enrollUserToTeam(){ //TODO its not complete yet

        int team_id = 8;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body("")
                .when()
                .put("/team/" + team_id + "/addUser")
                .then()
                .statusCode(200).log().all();
    }


    @Test
    public void assignTaskToTeam(){ //TODO its not complete yet

        int team_id = 8;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body("")
                .when()
                .put("/team/" + team_id + "/project/addProject")
                .then()
                .statusCode(200).log().all();
    }


    @Test
    public void tasksInTeam(){

        int team_id = 3;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body("")
                .when()
                .get("/team/" + team_id + "/tasks")
                .then()
                .statusCode(200).log().all();

    }






}
