package specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ReqquestSpecFactory {
    public static RequestSpecification getRequestSpec()
    {
        return new RequestSpecBuilder()
                .setBaseUri("https://conduit-api.bondaracademy.com/api")
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification getAuthRequestSpec(String token)
    {
        return new RequestSpecBuilder()
                .addRequestSpecification(getRequestSpec())
                .addHeader("Authorization", token)
                .build();
    }
}
