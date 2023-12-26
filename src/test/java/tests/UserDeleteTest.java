package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.Assertions;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;
import lib.BaseTestCase;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import lib.ApiCoreRequests;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic("Delete user")
@Feature("Deleting")
@Owner("Ivanov")
@Severity(CRITICAL)
public class UserDeleteTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Issue("DEL-123")
    @Description("This test checks deleting authorized user")
    @DisplayName("Test negative deleting user")
    public void testDeleteAuthorizedUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        //login
        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        //delete
        Response responseUserData = apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/2",header,cookie);

        Assertions.assertResponseCodeEquals(responseUserData, 400);

    }

    @Test
    @Issue("DEL-123")
    @Description("This test checks deleting just created user")
    @DisplayName("Test positive deleting user")
    public void testDeleteJustCreatedUser() {
        //generate user
        Map<String,String> userData= DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJsonRequest("https://playground.learnqa.ru/api/user/", userData);

        String userId=responseCreateAuth.getString("id");


        //login
        Map <String,String> authData=new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //delete
        Response responseUserData = apiCoreRequests
                .makeDeleteRequest(
                        "https://playground.learnqa.ru/api/user/"+userId,
                        this.getHeader(responseGetAuth,"x-csrf-token"),
                        this.getCookie(responseGetAuth,"auth_sid"));

        Assertions.assertResponseCodeEquals(responseUserData, 200);

    }

    @Test
    @Issue("DEL-123")
    @Description("This test checks deleting user with authorization of another user")
    @DisplayName("Test negative deleting user")
    public void testDeleteUserWithAnotherAuthData() {
        //generate user1
        Map<String,String> userData1= DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth1 = apiCoreRequests
                .makePostJsonRequest("https://playground.learnqa.ru/api/user/", userData1);

        String userId1=responseCreateAuth1.getString("id");

        //generate user2
        Map<String,String> userData2= DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth2 = apiCoreRequests
                .makePostJsonRequest("https://playground.learnqa.ru/api/user/", userData2);

        String userId2=responseCreateAuth2.getString("id");

        //login of user 1
        Map <String,String> authData=new HashMap<>();
        authData.put("email",userData1.get("email"));
        authData.put("password",userData1.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //delete
        Response responseUserData = apiCoreRequests
                .makeDeleteRequest(
                        "https://playground.learnqa.ru/api/user/"+userId2,
                        this.getHeader(responseGetAuth,"x-csrf-token"),
                        this.getCookie(responseGetAuth,"auth_sid"));


        Assertions.assertResponseCodeEquals(responseUserData, 400);

    }
}
