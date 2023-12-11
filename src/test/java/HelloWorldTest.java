import io.restassured.RestAssured;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HelloWorldTest {
    @Test
    public void testRestAssured() throws IOException {


        String filePath = "passwords.txt";
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = reader.readLine();
        String[] parts = line.split("\t");

        HashMap<String, List<String>> passwordMap = new HashMap<>();
        //записываем значения ключей в хэшмэп
        for (int i = 1; i < parts.length; i++)
            passwordMap.put(parts[i], new ArrayList<String>());


        int year = 2010;

        while ((line = reader.readLine()) != null) { //читаем строки их файла
            parts = line.split("\t");

            // записываем значения для ключей
            for (int i = 1; i < parts.length; i++)
                passwordMap.get(Integer.toString(year + i)).add(parts[i]);

        }
        reader.close();

        Boolean correctValue = false;

        for (List value : passwordMap.values()) {  // перебираем значения в хэшмэп
            for (int i = 0; i < value.size(); i++) {

                String adminPassword = value.get(i).toString();
                Map<String, String> data = new HashMap<>();
                data.put("login", "super_admin");
                data.put("password", adminPassword);

                Response responseForGet = RestAssured   //запрос на получение cookie
                        .given()
                        .body(data)
                        .when()
                        .get("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                        .andReturn();

                String responseCookie = responseForGet.getCookie("auth_cookie");

                Map<String, String> cookies = new HashMap<>();
                cookies.put("auth_cookie", responseCookie);

                Response responseForCheck = RestAssured   //запрос на проверку
                        .given()
                        .body(data)
                        .cookies(cookies)
                        .when()
                        .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                        .andReturn();

                ResponseBody result = responseForCheck.getBody();
                if (result.asString().equals("You are authorized")) {
                    System.out.println(result.asString() + ". Ваш пароль: " + adminPassword);

                    correctValue = true;
                }
                if (correctValue) break;
            }
            if (correctValue) break;
        }
    }
}