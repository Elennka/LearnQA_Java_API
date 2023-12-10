import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;





public class HelloWorldTest {
    @Test
    public void testRestAssured(){

        String urlTask = "https://playground.learnqa.ru/ajax/api/longtime_job";

//создание задачи
        JsonPath responseTask = RestAssured
            .get(urlTask)
            .jsonPath();
        String token = responseTask.get("token");
        int seconds = responseTask.get("seconds");

//Запрос сразу после получения token

        Map<String, String> params = new HashMap<>();
        params.put("token",token);
        JsonPath responseCheck = RestAssured
               .given()
               .queryParams(params)
               .get(urlTask)
               .jsonPath();

        String status = responseCheck.getString("status");

        if (status.equals("Job is NOT ready"))
            System.out.println("Задача еще не готова, нужно подождать " + seconds + " сек");

        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

// Запрос после истечени времени
        responseCheck = RestAssured
                .given()
                .queryParams(params)
                .get(urlTask)
                .jsonPath();
        String result = responseCheck.get("result");
        status = responseCheck.get("status");
        if (status.equals("Job is ready") && result!=null)
            System.out.println("Задача готова, результат = " + responseCheck.get("result"));
        else
            System.out.println("По истечении " + seconds + " сек задача так и не готова");
    }
}