package com.example.RestAssuredProject.Api;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
public class TestCaseOneTest {
    @Test
    public void test_1(){
        given().
                header("Content-Type","application/json").
                get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data.id[1]",equalTo(8))
                .log().all();
    }
}
