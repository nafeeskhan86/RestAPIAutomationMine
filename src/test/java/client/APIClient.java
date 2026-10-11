package client;


import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class APIClient {

    private final RequestSpecification requestSpec;

    public APIClient(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response post(String endpoint, Object requestBody) {
        return given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    public Response get(String endpoint) {
        return given()
                .spec(requestSpec)
                .when()
                .get(endpoint);
    }

    public Response put(String endpoint, Object requestBody) {
        return given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .put(endpoint);
    }

    public Response delete(String endpoint) {
        return given()
                .spec(requestSpec)
                .when()
                .delete(endpoint);
    }
}
