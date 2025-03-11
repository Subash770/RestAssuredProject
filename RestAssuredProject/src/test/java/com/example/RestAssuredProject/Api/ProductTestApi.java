package com.example.RestAssuredProject.Api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductTestApi {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/products";
    }

    @Test
    public void testCreateProductWithValidData() {
        String requestBody = "{ \"name\": \"Laptop\", \"price\": 1200.00, \"category\": \"Electronics\", \"description\": \"High-performance laptop\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("name", equalTo("Laptop"))
                .body("price", equalTo(1200.00f));
    }

    @Test
    public void testCreateProductWithOptionalFields() {
        String requestBody = "{ \"name\": \"Tablet\", \"price\": 500.00, \"description\": \"Android tablet\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("name", equalTo("Tablet"))
                .body("price", equalTo(500.00f));
    }

    @Test
    public void testCreateMultipleProducts() {
        String requestBody = "[{ \"name\": \"Phone\", \"price\": 800.00, \"category\": \"Electronics\" }, { \"name\": \"Monitor\", \"price\": 300.00, \"category\": \"Accessories\" }]";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/bulk")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(2));
    }

    @Test
    public void testCreateProductWithLongDescription() {
        String longDescription = "a".repeat(5000); // Generate 5000-character string
        String requestBody = "{ \"name\": \"Smartwatch\", \"price\": 200.00, \"description\": \"" + longDescription + "\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("name", equalTo("Smartwatch"));
    }

    //Negative Test cases
    @Test
    public void testCreateProductWithMissingFields() {
        String requestBody = "{ \"category\": \"Electronics\" }"; // Missing name & price

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(400) // Expecting Bad Request
                .body("error", containsString("Name is required"));
    }


    @Test
    public void testCreateProductWithMissingPrice() {
        String requestBody = "{ \"name\": \"Laptop\", \"category\": \"Electronics\" }"; // Missing price

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(400) // Expecting Bad Request
                .body("error", containsString("Price is required"));
    }
    @Test
    public void testCreateProductWithNegativePrice() {
        String requestBody = "{ \"name\": \"Headphones\", \"price\": -50.00, \"category\": \"Electronics\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(400) // Expecting Bad Request
                .body("error", containsString("Price is required and must be positive"));
    }

    @Test
    public void testCreateProductWithZeroPrice() {
        String requestBody = "{ \"name\": \"Mouse\", \"price\": 0, \"category\": \"Electronics\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(400) // Expecting Bad Request
                .body("error", containsString("Price is required and must be positive"));
    }

}
