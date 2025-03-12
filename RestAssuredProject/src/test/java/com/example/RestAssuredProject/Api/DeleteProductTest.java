package com.example.RestAssuredProject.Api;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("E-commerce API Testing")
@Feature("Product API Tests")
class DeleteProductTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:8080/products";
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Delete Existing Product")
    @Step("Create a product and delete it")
    @Description("Test to verify the successful deletion of an existing product from the database")
    void testDeleteExistingProduct() {
        // Step 1: Create a product first
        String requestBody = "{ \"name\": \"Phone\", \"price\": 500.00, \"category\": \"Electronics\", \"description\": \"Smartphone\" }";
        Response createResponse = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/")
                .then()
                .statusCode(200)
                .extract().response();

        int productId = createResponse.jsonPath().getInt("id");

        // Step 2: Delete the created product
        given()
                .when()
                .delete("/" + productId)
                .then()
                .statusCode(200)
                .body("message", equalTo("Product deleted successfully"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Delete Non-Existing Product")
    @Step("Attempt to delete a non-existing product")
    @Description("Test to verify the response when attempting to delete a product that does not exist in the database")
    void testDeleteNonExistingProduct() {
        // Attempt to delete a product that doesn't exist
        long nonExistingId = 99999;

        given()
                .when()
                .delete("/" + nonExistingId)
                .then()
                .statusCode(404)
                .body("error", equalTo("Product not found"));
    }
}
