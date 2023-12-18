package tests;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeWorkHeader {
    @Test
    public  void testRestAssured(){
        Response response= RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        Headers responseHeaders = response.getHeaders();

        assertTrue(responseHeaders.hasHeaderWithName("x-secret-homework-header"),"Response doesn't have 'x-secret-homework-header' header");
        assertEquals("Some secret value",responseHeaders.getValue("x-secret-homework-header"),"Header 'x-secret-homework-header' doesn't contains value 'Some secret value'");

    }
}
