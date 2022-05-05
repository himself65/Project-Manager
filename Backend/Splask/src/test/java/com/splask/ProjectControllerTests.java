package com.splask;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
public class ProjectControllerTests {

    @LocalServerPort
    int port;

    @Before
    public void setUp() throws Exception {
//        RestAssured.port = port;
//        RestAssured.baseURI = "http://localhost";
        baseURI = "http://coms-309-007.class.las.iastate.edu:8080";

    }


    @Test
    public void getALlProjects()
    {
        given().get("/project").then().statusCode(200).log().all();
    }


    @Test
    public void getProjectFail()
    {
        int projectId = 75;

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("")
                .when()
                .get("/project/" + projectId)
                .then()
                .statusCode(500).log().all();


    }


    @Test
    public void createProject()
    {
        JSONObject request = new JSONObject();

        request.put("projectName", "Build Lemonade Stand");
        request.put("username", "Chad");

        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post("/project")
                .then()
                .statusCode(200).log().all();
    }


    @Test
    public void createProjectFail()
    {
        JSONObject request = new JSONObject();

        request.put("projectName", "Build Lemonade Stand");
        request.put("username", "Chad");

        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post("/project")
                .then()
                .statusCode(200).log().all();
    }

    @Test
    public void getUsersInProject()
    {
        int projectId = 3;

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("")
                .get("/project/"+ projectId + "/users")
                .then()
                .statusCode(200).log().all();

    }


    @Test
    public void enrollUserToProject()
    {
        int projectId = 3;
        JSONObject request = new JSONObject();

        request.put("username", "andrew");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/project/"  + projectId + "/addUser")
                .then()
                .statusCode(200).log().all();

    }

    @Test
    public void enrollUserToProjectFail()
    {
        int projectId = 3;
        JSONObject request = new JSONObject();

        request.put("username", "Chad");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/project/"  + projectId + "/addUser")
                .then()
                .statusCode(200).log().all();
    }

    @Test
    public void getTeamsInProject()
    {
        int projectId = 2;

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get("/project/" + projectId + "/teams")
                .then()
                .statusCode(200).log().all();

    }


    @Test
    public void addTeamInProject()
    {
        int projectId = 2;
        JSONObject request = new JSONObject();

        request.put("teamName","Team UmiZoomi");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/project/" + projectId + "/addTeam")
                .then()
                .statusCode(200).log().all();
    }

    @Test
    public void addTeamInProjectFail()
    {
        int projectId = 1;
        JSONObject request = new JSONObject();

        request.put("teamName","Marketing Team");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/project/" + projectId + "/addTeam")
                .then()
                .statusCode(200).log().all();
    }

    @Test
    public void getTasksInProject()
    {
        int projectId = 1;

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get("/project/" + projectId + "/tasks")
                .then()
                .statusCode(200).log().all();

    }

    @Test
    public void addTasksToProject()
    {
        int projectId = 1;
        JSONObject request = new JSONObject();

        request.put("task","Make a pizza");
        request.put("team_id",2);

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/project/" + projectId + "/addTask")
                .then()
                .statusCode(200).log().all();
    }

    @Test
    public void addTasksToProjectTeamFail()
    {
        int projectId = 1;
        JSONObject request = new JSONObject();

        request.put("task","Tame a Lion");
        request.put("team_id",5);

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/project/" + projectId + "/addTask")
                .then()
                .statusCode(200).log().all();
    }



    @Test
    public void getAnnouncements()
    {
        int projectId = 1;

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("")
                .when()
                .get("/project/"+ projectId + "/announcements")
                .then()
                .statusCode(200).log().all();
    }

    @Test
    public void addAnnouncements()
    {
        int projectId = 1;
        JSONObject request = new JSONObject();

        request.put("announcement", "Brandon's sandwich is almost complete");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("project/" + projectId + "/addAnnouncement")
                .then()
                .statusCode(200).log().all();

    }





    }
