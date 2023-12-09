import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;


public class HelloWorldTest {
    @Test
    public void testRestAssured(){

       JsonPath response = RestAssured
               .get("https://playground.learnqa.ru/api/get_json_homework")
               .jsonPath();

       String messages = response.get("messages.message[1]");
       System.out.println(messages);



    }
}
