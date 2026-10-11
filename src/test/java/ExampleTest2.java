import client.APIClient;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.CreateArticleRequest;
import pojo.CreateArticleRequestBody;
import pojo.CreateArticleResponseBody;
import specification.ReqquestSpecFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.assertTrue;

public class ExampleTest2 {
    String authToken;

    @BeforeClass
    public void loginSetUp() {
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

//    @Test
//    public void getTags()
//    {
//        Response response = given().baseUri("https://conduit-api.bondaracademy.com/api")
//                .when().get("/tags");

    /// /                .then().statusCode(200).extract().response();
//        assertEquals(response.getStatusCode(),200);
//        JsonPath jsonPath = new JsonPath(response.asString());
//        List<String> tagsList = jsonPath.getList("tags");
//        assertTrue(tagsList.contains("Test"));
//    }
    @Test
    public void postArticle() throws IOException {
//        String requestBody = """
//                {
//                    "article": {
//                        "title": "Ertugrul1010",
//                        "description": "test desc",
//                        "body": "test body",
//                        "tagList": [
//                            "playwright"
//                        ]
//                    }
//                }
//                """;

        CreateArticleRequest article = new CreateArticleRequest();
        article.setTitle("Nafees1016");
        article.setDescription("Test Description");
        article.setBody("Test Body");
        article.setTagList(List.of("Nafees", "Khan"));
        CreateArticleRequestBody requestBody = new CreateArticleRequestBody(article);
        APIClient apiClient = new APIClient(ReqquestSpecFactory.getAuthRequestSpec(authToken));
        Response res = apiClient.post("/articles",requestBody);
//        Response res = given()
//                .spec(ReqquestSpecFactory.getAuthRequestSpec(authToken))
//                .body(requestBody)
//                .when()
//                .post("/articles");
//                .then()
//                .statusCode(201)
//                .extract().response();
        CreateArticleResponseBody responseBody = res.as(CreateArticleResponseBody.class);


//        CreateArticleResponseBody res = given().baseUri("https://conduit-api.bondaracademy.com/api")
//                .header("Content-Type", "application/json")
//            .header("Authorization", authToken)
//            .body(requestBody)
//            .when()
//            .post("/articles")
//            .then()
//            .statusCode(201)
//            .extract().as(CreateArticleResponseBody.class);
        System.out.println(article.getTitle()+"-----"+requestBody.getArticle().getTitle());
//        System.out.println(res.getBody().asString());
//        System.out.println(
//                Files.readString(
//                        Path.of("src/test/resources/schemas/postArticle-Response.json")
//                ));
        Assert.assertEquals(article.getTitle(), responseBody.getArticle().getTitle());
        File schemaFile = new File("src/test/resources/schemas/postArticle-Response.json");
        assertTrue(matchesJsonSchema(schemaFile).matches(res.asString()),"Response JSON schema validation failed");
//        assertThat(res.getBody().asString(), matchesJsonSchemaInClasspath("schemas/postArticle-Response.json"));
//        JsonPath jsonPath = new JsonPath(res.asString());
//        System.out.println(jsonPath.getString("article.title"));
    }


}
