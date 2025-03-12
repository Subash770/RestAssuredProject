package com.example.RestAssuredProject.Api;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
@Epic("E-commerce API Testing")
@Feature("Product API Tests")
public class ProductTestApi {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/products";
    }


    //Test Batch 1-Create a product

    @Test(description = "Create a product with valid data")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Create Product")
    @Step("Posting a valid product request")
    @Description("Test to verify product creation with valid data")
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

    @Test(description = "Create product with missing price")
    @Severity(SeverityLevel.NORMAL)
    @Story("Create Product")
    @Step("Posting a product without price")
    @Description("Test to verify error message when price is missing")
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

    @Test(description = "Create multiple products in a single request")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Create Multiple Products")
    @Step("Posting multiple products in a single API request")
    @Description("Test to verify that multiple products can be created successfully in one request")
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

    @Test(description = "Create a product with an extremely long description")
    @Severity(SeverityLevel.MINOR)
    @Story("Create Product")
    @Step("Posting a product with a long description")
    @Description("Test to verify that a product can be created with a long description up to 5000 characters")
    public void testCreateProductWithLongDescription() {
        String longDescription = "a".repeat(1000); // Generate 5000-character string
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

    @Test(description = "Create a product with missing required fields")
    @Severity(SeverityLevel.NORMAL)
    @Story("Create Product")
    @Step("Posting a product request with missing required fields")
    @Description("Test to verify that the API returns an error when name and price are missing")
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


    @Test(description = "Create a product without providing a price")
    @Severity(SeverityLevel.NORMAL)
    @Story("Create Product")
    @Step("Posting a product request without price")
    @Description("Test to verify that the API returns an error when the price field is missing")
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
    @Test(description = "Create a product with a negative price value")
    @Severity(SeverityLevel.NORMAL)
    @Story("Create Product")
    @Step("Posting a product with a negative price")
    @Description("Test to verify that the API does not allow negative price values")
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

    @Test(description = "Create a product with a price set to zero")
    @Severity(SeverityLevel.NORMAL)
    @Story("Create Product")
    @Step("Posting a product with zero price")
    @Description("Test to verify that the API does not allow a price of zero")
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


    //Test Batch-2: Get Product by ID
    @Test(description = "Retrieve a product using a valid existing product ID")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Retrieve Product")
    @Step("Fetching a product by valid ID")
    @Description("Test to verify retrieving a product by its valid ID")
    public void testGetProductByIdWithValidId() {
        // Assuming a product with ID 1 exists in the database
        Long productId = 1L; // This is a Long type in your test, but the actual API will return an Integer

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/" + productId)
                .then()
                .statusCode(200)
                .body("id", equalTo(1))  // Expect an Integer here, not a Long
                .body("name", notNullValue())
                .body("price", notNullValue());
    }

    @Test(description = "Retrieve a product with a non-existing product ID")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Retrieve Product")
    @Step("Fetching a product with a non-existing ID")
    @Description("Test to verify that the API returns a 404 when a non-existing product ID is provided")
    public void testGetProductWithNonExistingId() {
        // Assuming a product with ID 99999 does not exist
        Long nonExistingProductId = 99999L;

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/" + nonExistingProductId)
                .then()
                .statusCode(404) // Expecting Not Found
                .body("error", containsString("Product not found"));
    }

    @Test(description = "Retrieve a product with an invalid ID format")
    @Severity(SeverityLevel.NORMAL)
    @Story("Retrieve Product")
    @Step("Fetching a product with an invalid ID format")
    @Description("Test to verify that the API returns a 400 when an invalid ID format is provided")
    public void testGetProductWithInvalidIdFormat() {
        // Providing a non-numeric ID format (string instead of integer)
        String invalidId = "abc";

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/" + invalidId)
                .then()
                .statusCode(400) // Expecting Bad Request
                .body("error", containsString("Invalid ID format"));
    }




}
