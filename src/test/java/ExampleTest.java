import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

public class ExampleTest {
    String authToken;
    @BeforeClass
    public void loginSetUp()
    {
        String requestBody = """
            {
                "user": {
                    "email": "nafees.mca07@gmail.com",
                    "password": "khan1234"
                }
            }
            """;
        Response res = given().baseUri("https://conduit-api.bondaracademy.com/api")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users/login")
                .then()
                .statusCode(200)
                .extract()
                .response();
        JsonPath jsonPath = new JsonPath(res.asString());
        String Token = jsonPath.getString("user.token");
        authToken = "Token " + Token;
        System.out.println(authToken);
    }

    @Test
    public void getTags()
    {
        Response response = given().baseUri("https://conduit-api.bondaracademy.com/api")
                .when().get("/tags")
                .then().statusCode(200).extract().response();
        JsonPath jsonPath = new JsonPath(response.asString());
        List<String> tagsList = jsonPath.getList("tags");
        assertTrue(tagsList.contains("Test"));
    }

    @Test
    public void postArticle()
    {
        String requestBody = """
                {
                    "article": {
                        "title": "Ertugrul1010",
                        "description": "test desc",
                        "body": "test body",
                        "tagList": [
                            "playwright"
                        ]
                    }
                }
                """;
        Response res = given().baseUri("https://conduit-api.bondaracademy.com/api")
                .header("Content-Type", "application/json")
                .header("Authorization", authToken)
                .body(requestBody)
                .when()
                .post("/articles")
                .then()
                .statusCode(201)
                .extract().response();
        assertThat(res.getBody().asString(),matchesJsonSchemaInClasspath("schemas/postArticle-Response.json"));
        JsonPath jsonPath = new JsonPath(res.asString());
        System.out.println(jsonPath.getString("article.title"));
    }



}
