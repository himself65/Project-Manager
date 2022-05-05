package com.splask;

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
public class TaskControllerTests {

    @LocalServerPort
    int port;

    @Before
    public void setUp() throws Exception {
//        RestAssured.port = port;
//        RestAssured.baseURI = "http://localhost";
        baseURI = "http://coms-309-007.class.las.iastate.edu:8080";

    }


    @Test
    public void getAllTasks() {

        given().get("/task").then().statusCode(200).log().all();
    }


    @Test
    public void createNewTask(){ //TODO assign the task

        JSONObject request = new JSONObject();

//        request.put();

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post("/task") //endpoint
                .then()
                // The status code varies depending on the call, if it's a PUT, DELETE, GET, POST, etc
                .statusCode(200).log().all();
    }



    @Test
    public void enrollUserToTask(){ //TODO assign the task

        JSONObject request = new JSONObject();

//        request.put();
        int task_id = 1;
        int user_id = 1;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/" + task_id + "/users/" + user_id)
                .then()
                .statusCode(200).log().all();
    }


    @Test
    public void assignTaskToTeam(){ //TODO assign the task to team

        JSONObject request = new JSONObject();

//        request.put();
        int task_id = 1;
        int team_id = 1;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/task/" + task_id + "/add")
                .then()
                .statusCode(200).log().all();
    }


//    @Test
//    public void assignTaskToProject(){ //TODO assign the task to project
//
//        JSONObject request = new JSONObject();
//
////        request.put();
//        int task_id = 1;
////        int project_id = 1;
//
//        given().contentType(ContentType.JSON).accept(ContentType.JSON)
//                .body(request.toJSONString())
//                .when()
//                .put("/task/" + task_id + "/project/" +  project_id)
//                .then()
//                .statusCode(200).log().all();
//    }


    @Test
    public void setCompleteTask(){ //TODO assign the task to project

        JSONObject request = new JSONObject();

//        request.put();
        int task_id = 1;
//        int project_id = 1;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/task/" + task_id + "/complete")
                .then()
                .statusCode(200).log().all();
    }


    @Test
    public void setDescription(){ //TODO assign description

        JSONObject request = new JSONObject();

//        request.put();
        int task_id = 1;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/task/" + task_id + "/description")
                .then()
                .statusCode(200).log().all();
    }



    }
