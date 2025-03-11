package com.example.RestAssuredProject.Api;
//static import to use the methods directly without creating objects
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestGet {
    @Test
    void Test01(){
        //Using objects without using static imports
       Response response= RestAssured.get("https://reqres.in/api/users?page=2");
        System.out.println(response.asString());
        System.out.println(response.getBody().asString());
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.getHeader("content-type"));
        System.out.println(response.getTime());
        int statusCode =response.getStatusCode();
        Assert.assertEquals(statusCode,200);

    }
    @Test
    void  Test02(){
        //using static imports,we can use functions without the need to create objects
        given()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data.id[0]",equalTo(7));
    }
}
