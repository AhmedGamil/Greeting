package com.telenor.greeting;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GreetingApiTest {

    private static final String HOST = "http://localhost:8080";

    @BeforeAll
    public static void enableRestAssureLoggingForFailingTests() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void verify_server_liveness_prob() {
        when()
                .get(HOST + "/ping")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void greeting_account_personal_valid_id_then_200_ok() {
        String responseBody = given()
                .queryParam("account", "personal")
                .queryParam("id", 123)
                .when()
                .get(HOST + "/greeting")
                .then().statusCode(HttpStatus.OK.value())
                .extract().asString();
        assertThat(responseBody, equalTo("Hi, userId 123"));

    }

    @Test
    public void greeting_account_personal_with_type_then_400_bad_request() {
        //account=personal and type=big then 400
        given()
                .queryParam("account", "personal")
                .queryParam("type", "big")
                .when()
                .get(HOST + "/greeting")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
        //account=personal type=small then 400
        given()
                .queryParam("account", "personal")
                .queryParam("type", "small")
                .when()
                .get(HOST + "/greeting")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void greeting_account_business_with_id_then_400_bad_request() {
        given()
                .queryParam("account", "business")
                .queryParam("id", "123")
                .when()
                .get(HOST + "/greeting")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void greeting_account_personal_with_non_positive_id_then_bad_request_error() {
        given()
                .queryParam("account", "personal")
                .queryParam("id", -123)
                .when()
                .get(HOST + "/greeting")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void greeting_account_with_invalid_id_then_bad_request_error() {
        given()
                .queryParam("account", "wrongAccount")
                .queryParam("id", 123)
                .when()
                .get(HOST + "/greeting")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void greeting_account_business_type_big_then_correct_greeting_message() {
        String responseBody = given()
                .queryParam("account", "business")
                .queryParam("type", "BIG")
                .when()
                .get(HOST + "/greeting")
                .then().statusCode(HttpStatus.OK.value())
                .extract().asString();
        assertThat(responseBody, equalTo("Welcome, business user!"));
    }

    @Test
    public void greeting_account_business_type_small_then_501_not_yet_implemented() {
        given()
                .queryParam("account", "business")
                .queryParam("type", "SMALL")
                .when()
                .get(HOST + "/greeting")
                .then()
                .statusCode(HttpStatus.NOT_IMPLEMENTED.value());
    }

    @Test
    public void greeting_account_business_wrong_type_then_400_bad_request() {
        given()
                .queryParam("account", "business")
                .queryParam("type", "wrong-type")
                .when()
                .get(HOST + "/greeting")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


}
