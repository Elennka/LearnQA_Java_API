import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class HelloWorldTest {
    @Test
    public void testRestAssured(){

        String locationHeader = "https://playground.learnqa.ru/api/long_redirect";
        int statusCode;
        int i = 1;

        do {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(locationHeader)
                    .andReturn();

            locationHeader = response.getHeader("Location");
            statusCode= response.getStatusCode();
            if (statusCode != 200)
                System.out.println("Редирект "+ i +": " + locationHeader);
            i++;
        } while ((statusCode!=200) && (locationHeader!=null));
    }
}