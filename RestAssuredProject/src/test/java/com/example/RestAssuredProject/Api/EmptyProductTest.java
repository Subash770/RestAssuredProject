package com.example.RestAssuredProject.Api;

import com.example.RestAssuredProject.repository.ProductRepository;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
@Epic("E-commerce API Testing")
@Feature("Product API Tests")
public class EmptyProductTest {

    @Autowired
    private ProductRepository productRepository;

    // Setup: Clean up before tests
    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();  // Clean database before each test
    }

    // Test case for empty products
    // Test case for empty products
    @Test(description = "Test to verify the empty product list")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Retrieve Products")
    @Step("Get all products when the list is empty")
    @Description("Test to verify that the API returns an empty list when no products are present in the database")

    public void testGetAllProductsWhenEmpty() {
        // Assuming the database is empty (Ensure no products exist)

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("size()", equalTo(0)); // Expecting an empty list
    }


    // Teardown: Clean up after tests
    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();  // Clean database after each test
    }
}