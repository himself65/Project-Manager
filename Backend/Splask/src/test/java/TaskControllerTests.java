import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@SpringBootTest(classes = Splask.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TaskControllerTests {

    @LocalServerPort
    int port;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }


    @Test
    public void getAllTasks() {

        given().get("/task").then().statusCode(200).log().all();
    }


//    @Test
//    public void createNewTask(){ //TODO --> WHAT IS THE PARAMETER FOR THE TASK
//
//        JSONObject request = new JSONObject();
//        request.put("task", "Eat Fried Chicken");
//
//        given().contentType(ContentType.JSON).accept(ContentType.JSON)
//                .body(request.toJSONString())
//                .when()
//                .post("/task") //endpoint
//                .then()
//                .statusCode(200).log().all();
//    }



//    @Test
//    public void enrollUserToTask(){ //TODO --> figure out how to assign the usre to the task, throwing 500 server error
//
//        int task_id = 2;
//        int user_id = 16;
//
//        given().contentType(ContentType.JSON).accept(ContentType.JSON)
//                .body("")
//                .when()
//                .put("/" + task_id + "/users/" + user_id)
//                .then()
//                .statusCode(200).log().all();
//    }


//    @Test
//    public void assignTaskToTeam(){ //TODO --> figure how to send the Team by the ID
//
//        int task_id = 1;
//        int team_id = 1;
//
//        given().contentType(ContentType.JSON).accept(ContentType.JSON)
//                .body("")
//                .when()
//                .put("/task/" + task_id + "/add")
//                .then()
//                .statusCode(200).log().all();
//    }


//    @Test
//    public void assignTaskToProject(){ //TODO --> Test
//
//        int task_id = 1;
//        int project_id = 1;
//
//        given().contentType(ContentType.JSON).accept(ContentType.JSON)
//                .body("")
//                .when()
//                .put("/task/" + task_id + "/project/" +  project_id)
//                .then()
//                .statusCode(200).log().all();
//    }


    @Test
    public void setCompleteTask(){

        JSONObject request = new JSONObject();
        request.put("username", "Demo4");
        int task_id = 1;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/task/" + task_id + "/complete")
                .then()
                .statusCode(200).log().all();
    }


    @Test
    public void setDescription(){

        JSONObject request = new JSONObject();
        request.put("description","Please go to Popeyes and buy a 20pc bucket and a family size fries");
        int task_id = 2;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/task/" + task_id + "/description")
                .then()
                .statusCode(200).log().all();
    }


}
