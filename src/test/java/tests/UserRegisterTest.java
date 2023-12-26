package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import lib.ApiCoreRequests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@Epic("Create user cases")
@Feature("Creation")
public class UserRegisterTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This test check the creation user with existing email")
    @DisplayName("Test negative create user")
    public void testCreateUserWithExistingEmail() {
        String email = "vikkotov@example.com";
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");
    }

    @Test
    @Description("This test successfully create user by valid email and registration data")
    @DisplayName("Test positive create user")
    public void testCreateUserSuccessfully() {

        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);


        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");

    }

    @Test
    @Description("This test check the creation user with wrong email")
    @DisplayName("Test negative create user")
    public void testCreateUserWithWrongEmail() {
        String email = DataGenerator.getWrongRandomEmail();
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    }


    @Description("This test check the creation user without sending one of parameters: " +
            "username, firstName, lastName, email, password")
    @DisplayName("Test negative create user")
    @ParameterizedTest
    @ValueSource(strings = {"firstName,lastName,email,password",
            "username,lastName,email,password",
            "username,firstName,email,password",
            "username,firstName,lastName,password",
            "username,firstName,lastName,email"})

    public void testNegativeCreateUser(String Condition) {

        Map<String, String> userData = new HashMap<>();
        userData = DataGenerator.getRegistrationData(Condition);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    }

    @Test
    @Description("This test check the creation user with username of 1 symbol")
    @DisplayName("Test negative create user")
    public void testCreateUserWithShortUserName() {

        Map<String, String> userData = new HashMap<>();
        userData = DataGenerator.getRegistrationData();
        userData.put("username", DataGenerator.getRandomUsername(1));

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    }

    @Test
    @Description("This test check the creation user with username of 250 symbol")
    @DisplayName("Test positive create user")
    public void testCreateUserWithLongUserName() {

        Map<String, String> userData = new HashMap<>();
        userData = DataGenerator.getRegistrationData();
        userData.put("username", DataGenerator.getRandomUsername(250));

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
    }

}
