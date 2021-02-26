package mobileappwebservicesrestassuredtest;

import com.google.common.collect.HashBiMap;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.catalina.User;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersWebserviceEndpointTest {

    private final String CONTEXT_PATH = "/mobile-app-ws";
    private final String EMAIL_ADDRESS = "sergey.kargopolov@swiftdeveloperblo.com";
    private final String JSON = "application/json";
    private static String authorisationHeader;
    private static String userId;

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

        authorisationHeader = response.header("Authorization");
        userId = response.header("UserID");

        assertNotNull(authorisationHeader);
        assertNotNull(userId);
    }

    //testGetUserDetails
    @Test
    final void b() {
        Response response = given()
                .pathParam("id", userId)
                .header("Authorization", authorisationHeader)
                .accept(JSON)
                .when()
                .get(CONTEXT_PATH + "/users/{id}")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract()
                .response();

        String userPublicId = response.jsonPath().getString("userId");
        String userEmail = response.jsonPath().getString("email");
        String firstName = response.jsonPath().getString("firstName");
        String lastName = response.jsonPath().getString("lastName");
        List<Map<String, String>> addresses = response.jsonPath().getList("addresses");
        String addressId = addresses.get(0).get("addressId");

        assertNotNull(userPublicId);
        assertNotNull(userEmail);
        assertNotNull(firstName);
        assertNotNull(lastName);
        assertEquals(EMAIL_ADDRESS, userEmail);
        assertTrue(addresses.size() == 2);
        assertTrue(addressId.length() == 30);

    }

    //test update user Details
    @Test
    final void c() {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("firstName", "Serge");
        userDetails.put("lastName", "KG");

        Response response = given()
                .contentType(JSON)
                .accept(JSON)
                .header("Authorization", authorisationHeader)
                .pathParam("id", userId)
                .body(userDetails)
                .when()
                .put(CONTEXT_PATH + "/users/{id}")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract()
                .response();

        String firstName = response.jsonPath().getString("firstName");
        String lastName = response.jsonPath().getString("lastName");
        List<Map<String, String>> storedAddresses = response.jsonPath().getList("addresses");

        assertEquals("Serge", firstName);
        assertEquals("KG", lastName);
        assertNotNull(storedAddresses);
        assertTrue(storedAddresses.size() == 2);
    }

    //test delete user details
    @Test
    final void d() {

        Response response = given()
                .header("Authorization", authorisationHeader)
                .accept(JSON)
                .pathParam("id", userId)
                .when()
                .delete(CONTEXT_PATH + "/users/{id}")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract()
                .response();

        String operationResult = response.jsonPath().getString("operationResult");
        assertEquals("SUCCESS", operationResult);

    }
}
