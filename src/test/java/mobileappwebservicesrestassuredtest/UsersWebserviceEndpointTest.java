package mobileappwebservicesrestassuredtest;

import com.google.common.collect.HashBiMap;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersWebserviceEndpointTest {

    private final String CONTEXT_PATH = "/mobile-app-ws";
    private final String EMAIL_ADDRESS = "sergey.kargopolov@swiftdeveloperblo.com";
    private final String JSON = "application/json";

    @BeforeEach
    void setUp() throws Exception {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    //testUserLogin
    @Test
    final void a() {

        Map<String, String> loginDetails = new HashMap<>();
        loginDetails.put("email", EMAIL_ADDRESS);
        loginDetails.put("password", "123");

        Response response = given().contentType(JSON)
                .accept(JSON)
                .body(loginDetails)
                .when()
                .post(CONTEXT_PATH + "/users/login")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String authorisationHeader = response.header("Authorization");
        String userId = response.header("UserID");

        assertNotNull(authorisationHeader);
        assertNotNull(userId);
    }


}
