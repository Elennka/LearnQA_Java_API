package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import lib.ApiCoreRequests;

import static lib.DataGenerator.getRandomUsername;
import static lib.DataGenerator.getWrongRandomEmail;

@Epic("Editing user")
@Feature("Editing")
public class UserEditTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    @Description("This test checks the editing just created user")
    @DisplayName("Test positive editing user")
    public void testEditJustCreatedTest(){
        //GENERATE USER
        Map<String,String> userData= DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJsonRequest("https://playground.learnqa.ru/api/user/", userData);

        String userId=responseCreateAuth.getString("id");

        //LOGIN
        Map <String,String> authData=new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);


        //EDIT
        String newName = "Changed Name";
        Map<String,String> editData=new HashMap<>();
        editData.put("firstName",newName);

        Response responseEditUser = apiCoreRequests
                .makePutRequest(
                        "https://playground.learnqa.ru/api/user/"+userId,
                        this.getHeader(responseGetAuth,"x-csrf-token"),
                        this.getCookie(responseGetAuth,"auth_sid"),
                        editData);

        //GET
        Response responseUserData = apiCoreRequests
                .makeGetRequest(
                        "https://playground.learnqa.ru/api/user/"+userId,
                        this.getHeader(responseGetAuth,"x-csrf-token"),
                        this.getCookie(responseGetAuth,"auth_sid"));

        Assertions.asserJsonByName(responseUserData,"firstName", newName);
    }

    @Test
    @Description("This test checks the editing user without auth")
    @DisplayName("Test negative editing user")
    public void testEditUserWithoutAuth() {
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJsonRequest("https://playground.learnqa.ru/api/user/", userData);

        String userId = responseCreateAuth.getString("id");


        //EDIT
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests
                .makePutRequest(
                        "https://playground.learnqa.ru/api/user/" + userId,
                        editData);

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
    }

        @Test  ////ПЕРЕПРОВЕРИТЬ ТЕСТ, ОН ПАДАЕТ
        @Description("This test checks the editing user with authorisation data of another user")
        @DisplayName("Test negative editing user")
        public void testEditUserWithAnotherAuthData(){
            //GENERATE USER1
            Map<String,String> userData1= DataGenerator.getRegistrationData();

            JsonPath responseCreateAuth1 = apiCoreRequests
                    .makePostJsonRequest("https://playground.learnqa.ru/api/user/", userData1);

            String userId1=responseCreateAuth1.getString("id");

            //GENERATE USER2
            Map<String,String> userData2= DataGenerator.getRegistrationData();

            JsonPath responseCreateAuth2 = apiCoreRequests
                    .makePostJsonRequest("https://playground.learnqa.ru/api/user/", userData2);

            String userId2=responseCreateAuth2.getString("id");


            //LOGIN USER1
            Map <String,String> authData=new HashMap<>();
            authData.put("email",userData1.get("email"));
            authData.put("password",userData1.get("password"));

            Response responseGetAuth = apiCoreRequests
                    .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);


            //EDIT
            String newName = "Changed Name";
            Map<String,String> editData=new HashMap<>();
            editData.put("firstName",newName);

            Response responseEditUser = apiCoreRequests
                    .makePutRequest(
                            "https://playground.learnqa.ru/api/user/"+userId2,
                            this.getHeader(responseGetAuth,"x-csrf-token"),
                            this.getCookie(responseGetAuth,"auth_sid"),
                            editData);

            Assertions.assertResponseCodeEquals(responseEditUser, 400);
        }

    @Test
    @Description("This test checks the editing just created user with wrong email")
    @DisplayName("Test negative editing user")
    public void testEditEmailJustCreatedTest(){
        //GENERATE USER
        Map<String,String> userData= DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJsonRequest("https://playground.learnqa.ru/api/user/", userData);

        String userId=responseCreateAuth.getString("id");

        //LOGIN
        Map <String,String> authData=new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);


        //EDIT
        String newEmail = getWrongRandomEmail();
        Map<String,String> editData=new HashMap<>();
        editData.put("email",newEmail);

        Response responseEditUser = apiCoreRequests
                .makePutRequest(
                        "https://playground.learnqa.ru/api/user/"+userId,
                        this.getHeader(responseGetAuth,"x-csrf-token"),
                        this.getCookie(responseGetAuth,"auth_sid"),
                        editData);

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
    }
    @Test
    @Description("This test checks the editing just created user with FirstName of 1Symbol")
    @DisplayName("Test negative editing user")
    public void testEditFirstNameJustCreatedTest(){
        //GENERATE USER
        Map<String,String> userData= DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJsonRequest("https://playground.learnqa.ru/api/user/", userData);

        String userId=responseCreateAuth.getString("id");

        //LOGIN
        Map <String,String> authData=new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);


        //EDIT
        String newFirstName = getRandomUsername(1);
        Map<String,String> editData=new HashMap<>();
        editData.put("firstName",newFirstName);

        Response responseEditUser = apiCoreRequests
                .makePutRequest(
                        "https://playground.learnqa.ru/api/user/"+userId,
                        this.getHeader(responseGetAuth,"x-csrf-token"),
                        this.getCookie(responseGetAuth,"auth_sid"),
                        editData);

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
    }
}
