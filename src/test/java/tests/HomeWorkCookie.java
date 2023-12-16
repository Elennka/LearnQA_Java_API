package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeWorkCookie {

    @Test
    public  void testRestAssured(){
        Response response= RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        Map<String,String> responseCookies = response.getCookies();
        assertTrue(responseCookies.containsKey("HomeWork"),"Response doesn't have 'HomeWork' cookie");
        assertEquals("hw_value",response.getCookie("HomeWork"),"Cockie 'HomeWork' doesn't contains value 'hm_value'");

    }
}
