package app.docuport.utilities;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

public class DocuportApiUtil {


    public static String getAccessToken(String email, String password){

        String jsonBody = "{\n" +
                "\"usernameOrEmailAddress\": \"" +email+"\",\n" +
                "\"password\": \"" + password + "\"\n" +
                "}";

        String accessToken = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(jsonBody)
                .when().post(Environment.BASE_URL + "/api/v1/authentication/account/authenticate")
                .then().assertThat().statusCode(200)
                .and().extract().path("user.jwtToken.accessToken");

//       System.out.println("accessToken = " + accessToken);
//        assertThat("accessToken is empty or null", accessToken, not(emptyOrNullString()));

        return "Bearer " + accessToken;
    }

    public static String getAccessToken(String userType){
        String jsonBody = "{\n" +
                "  \"usernameOrEmailAddress\": \"b1g1_"+userType.toLowerCase()+"@gmail.com\",\n" +
                "  \"password\": \"Group1\"\n" +
                "}";

        String accessToken = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when().post("https://beta.docuport.app/api/v1/authentication/account/authenticate")
                .then().statusCode(HttpStatus.SC_OK)
                .extract().path("user.jwtToken.accessToken");

        assertTrue(accessToken!=null && !accessToken.isEmpty());

        return accessToken;
    }

}
