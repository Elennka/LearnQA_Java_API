package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserAgentTest {

    @ParameterizedTest
    @CsvSource(value = {"'Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30','Mobile','No','Android'",
            "'Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1','Mobile','Chrome','iOS'",
            "'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html','Googlebot','Unknown','Unknown'",
            "'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0','Web','Chrome','No'",
            "'Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1','Mobile','No','iPhone'"
    })
    public void UserAgentTest(String name, String expectedPlatform, String expectedBrowser, String expectedDevice) {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", name);
        JsonPath response = RestAssured
                .given()
                .headers(headers)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();

        String actualPlatform = response.get("platform");
        String actualBrowser = response.get("browser");
        String actualDevice = response.get("device");


        assertAll("name",
                () -> assertEquals(expectedPlatform, actualPlatform, "User-Agent String: "+name+". Неправильная платформа"),
                () -> assertEquals(expectedBrowser, actualBrowser, "User-Agent String: "+name+".Неправильный браузер"),
                () -> assertEquals(expectedDevice, actualDevice, "User-Agent String: "+name+".Неправильное устройство")
        );


    }
}
