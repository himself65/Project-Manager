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
public class TeamControllerTests {

    @LocalServerPort
    int port;

    @Before
    public void setUp() throws Exception{
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }


    @Test
    public void getAllTeams() {

        given().get("/team").then().statusCode(200).log().all();
    }


    @Test
    public void createNewTeam(){

        JSONObject request = new JSONObject();
        request.put( "teamName" , "team666");

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
    public void enrollUserToTeam(){

        JSONObject request = new JSONObject();
        request.put( "username" , "Demo4");
        int team_id = 6;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/team/" + team_id + "/addUser")
                .then()
                .statusCode(200).log().all();
    }


//    @Test
//    public void assignTaskToTeam(){
//
//        JSONObject request = new JSONObject();
//        request.put( "taskname" , "Create logout page");
//        int team_id = 5;
//
//        given().contentType(ContentType.JSON).accept(ContentType.JSON)
//                .body(request.toJSONString())
//                .when()
//                .put("/team/" + team_id + "/project/addProject")
//                .then()
//                .statusCode(200).log().all();
//    }


    @Test
    public void tasksInTeam(){

        int team_id = 5;

        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body("")
                .when()
                .get("/team/" + team_id + "/tasks")
                .then()
                .statusCode(200).log().all();
    }


}
