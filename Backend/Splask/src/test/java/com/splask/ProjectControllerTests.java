package com.splask;

import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.baseURI;

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





    }
