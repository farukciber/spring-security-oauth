package com.baeldung.newstack;

import static io.restassured.RestAssured.given;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class CustomClaimsValidatorTest {

    private static final String CLIENT_ID = "newClient";
    private static final String CLIENT_SECRET = "newClientSecret";
    private static final String TOKEN_URL = "http://localhost:8083/auth/realms/baeldung/protocol/openid-connect/token";
    private static final String RESOURCE_URL = "http://localhost:8081/new-resource-server/api/projects";

    @Test
    public void givenValidUsername_whenGetResource_thenOk() {
        String accessToken = getAccessToken("john@baeldung.com", "123");

        given().auth()
                .oauth2(accessToken)
                .get(RESOURCE_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void givenInvalidUsername_whenGetResource_thenUnauthorized() {
        String accessToken = getAccessToken("john@test.com", "123");

        given().auth()
                .oauth2(accessToken)
                .get(RESOURCE_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private String getAccessToken(String username, String password) {
        return given()
                .auth().preemptive().basic(CLIENT_ID, CLIENT_SECRET)
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "password")
                .formParam("username", username)
                .formParam("password", password)
                .formParam("scope", "read write")
                .when()
                .post(TOKEN_URL).then().extract().path("access_token");
    }
}
