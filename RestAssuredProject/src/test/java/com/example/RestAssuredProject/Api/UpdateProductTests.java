package com.example.RestAssuredProject.Api;

import com.example.RestAssuredProject.model.Product;
import com.example.RestAssuredProject.repository.ProductRepository;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("E-commerce API Testing")
@Feature("Product API Tests")
public class UpdateProductTests {

    @Autowired
    private ProductRepository productRepository;

    private Long productId;

    @BeforeEach
    public void setUp() {
        // Clean up before each test
        productRepository.deleteAll();

        // Create a sample product and retrieve the ID
        Product product = new Product(null, "Laptop", 1000.00, "Electronics", "A high-performance laptop");
        product = productRepository.save(product);
        productId = product.getId(); // Store the ID for testing update
    }


    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Update Product with Non-Existing ID")
    @Step("Updating a product with an invalid ID")
    @Description("Test to verify that updating a product with a non-existing ID returns 404")
    public void testUpdateProductWithNonExistingId() {
        String updatedProductJson = "{ \"name\": \"Tablet\", \"price\": 500.00, \"category\": \"Electronics\", \"description\": \"A brand new tablet\" }";

        given()
                .contentType(ContentType.JSON)
                .body(updatedProductJson)
                .when()
                .put("/products/9999") // Non-existing ID
                .then()
                .statusCode(404)
                .body("error", equalTo("Product not found"));
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll(); // Cleanup database
    }
}
